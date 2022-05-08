package com.knuipalab.dsmp.service.project;

import com.knuipalab.dsmp.configuration.auth.dto.SessionUser;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.project.ProjectRepository;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.domain.user.UserRepository;
import com.knuipalab.dsmp.dto.project.ProjectInviteRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectOustRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.ProjectNotFoundException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UnAuthorizedAccessException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UserEmailBadRequestException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UserNotFoundException;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import com.knuipalab.dsmp.service.storage.StorageService;
import com.knuipalab.dsmp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final HttpSession httpSession;

    private final ProjectRepository projectRepository;

    private final MetaDataService metaDataService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final StorageService storageService;


    @Transactional (readOnly = true)
    public List<ProjectResponseDto> findByCreator(){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        ObjectId creatorId = new ObjectId(sessionUser.getUserId());

        List <Project> projectList = projectRepository.findByCreator(creatorId);

        return projectList.stream()
                .map( project -> new ProjectResponseDto(project))
                .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public List<ProjectResponseDto> findInvitedProject(){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        ObjectId userId = new ObjectId(sessionUser.getUserId());

        List <Project> projectList = projectRepository.findInvisitedProject(userId);

        return projectList.stream()
                .map( project -> new ProjectResponseDto(project))
                .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public ProjectResponseDto findById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        return new ProjectResponseDto(project);
    }

    @Transactional
    public void insert(ProjectRequestDto projectRequestDto){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        String userId = sessionUser.getUserId();

        Project project = new Project().builder()
                .projectName(projectRequestDto.getProjectName())
                .creator(userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)))
                .build();

        projectRepository.save(project);

    }

    @Transactional
    public void update(String projectId,ProjectRequestDto projectRequestDto){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        project.update(projectRequestDto.getProjectName());

        projectRepository.save(project);
    }

    @Transactional
    public void deleteById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        String userId = sessionUser.getUserId();

        if(!project.getCreator().getUserId().equals(userId)){
            throw new UnAuthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        metaDataService.deleteAllByProjectId(projectId);

        storageService.deleteAll(projectId);

        projectRepository.delete(project);
    }

    @Transactional
    public void invite(ProjectInviteRequestDto projectInviteRequestDto) {

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        Project project = projectRepository.findById(projectInviteRequestDto.getProjectId())
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        if(!sessionUser.getUserId().equals(project.getCreator().getUserId())){
            throw new UnAuthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS); // 프로젝트 생성자가 아니면 접근 권한 에러
        }

        List<User> userList = new ArrayList<>();
        List<String> notExistUserEmailList = new ArrayList<>();

        List<String> existUserEmailList = projectInviteRequestDto.getEmailList().stream()
                .filter( email -> {
                    Optional<User> optionalUser = userService.findUserByEmail(email);
                    if( !optionalUser.isPresent()){
                        notExistUserEmailList.add(email);
                        return false;
                    } else {
                        userList.add(optionalUser.get());
                        return true;
                    }
                })
                .collect(Collectors.toList());

        project.invite(userList);

        projectRepository.save(project);

        if(!notExistUserEmailList.isEmpty()){
            throw new UserEmailBadRequestException(ErrorCode.USER_EMAIL_BAD_REQUEST, new ArrayList<Object>(notExistUserEmailList));
        }

    }

    @Transactional
    public void oustByEmailList(ProjectOustRequestDto projectOustRequestDto) {

        Project project = projectRepository.findById(projectOustRequestDto.getProjectId())
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        List<User> userList = new ArrayList<>();

        List<String> notExistUserEmailList = new ArrayList<>();

        List<String> existUserEmailList = projectOustRequestDto.getEmailList().stream()
                .filter( email -> {
                    Optional<User> optionalUser = userService.findUserByEmail(email);
                    if( !optionalUser.isPresent()){
                        notExistUserEmailList.add(email);
                        return false;
                    } else {
                        userList.add(optionalUser.get());
                        return true;
                    }
                })
                .collect(Collectors.toList());

        project.oust(existUserEmailList);

        projectRepository.save(project);

        if(!notExistUserEmailList.isEmpty()){
            throw new UserEmailBadRequestException(ErrorCode.USER_EMAIL_BAD_REQUEST, new ArrayList<Object>(notExistUserEmailList));
        }

    }

    @Transactional
    public void oust(String projectId) {

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new ProjectNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        project.oust(sessionUser.getEmail());

        projectRepository.save(project);

    }

}
