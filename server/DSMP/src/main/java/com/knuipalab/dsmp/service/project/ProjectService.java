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
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import com.knuipalab.dsmp.service.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Transactional (readOnly = true)
    public List<ProjectResponseDto> findByCreator(){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        List <ProjectResponseDto> projectResponseDtoList = new ArrayList<ProjectResponseDto>();

        ObjectId creatorId = new ObjectId(sessionUser.getUserId());

        List <Project> projectList = projectRepository.findByCreator(creatorId);

        for(Project project: projectList){
            projectResponseDtoList.add(new ProjectResponseDto(project));
        }

        return projectResponseDtoList;
    }

    @Transactional (readOnly = true)
    public ProjectResponseDto findById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        return new ProjectResponseDto(project);
    }

    @Transactional
    public void insert(ProjectRequestDto projectRequestDto){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
        String userId = sessionUser.getUserId();
        Project project = new Project().builder()
                .projectName(projectRequestDto.getProjectName())
                .creator(userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("해당 userId 값을 가진 사용자 정보가 없습니다.")))
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void update(String projectId,ProjectRequestDto projectRequestDto){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        project.update(projectRequestDto.getProjectName());

        projectRepository.save(project);
    }

    @Transactional
    public void deleteById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        metaDataService.deleteAllByProjectId(projectId);

        projectRepository.delete(project);
    }

    @Transactional
    public void invite(String projectId,ProjectInviteRequestDto projectInviteRequestDto) {

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        if(!sessionUser.getUserId().equals(project.getCreator().getUserId())){
            throw new IllegalArgumentException("프로젝트 생성자만이 초대를 진행할 수 있습니다.");
        }

        List<User> userList = projectInviteRequestDto.getEmailList().stream()
                .map( email -> userService.findUserByEmail(email))
                .collect(Collectors.toList());

        project.invite(userList);

        projectRepository.save(project);

    }

    @Transactional
    public void oust(String projectId, ProjectOustRequestDto projectOustRequestDto) {

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        if(!sessionUser.getUserId().equals(project.getCreator().getUserId())){
            throw new IllegalArgumentException("프로젝트 생성자만이 방문자 삭제를 진행할 수 있습니다.");
        }

        project.oust(projectOustRequestDto.getEmailList());

        projectRepository.save(project);

    }
}
