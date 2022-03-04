package com.knuipalab.dsmp.service.project;


import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.project.ProjectRepository;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.domain.user.UserRepository;
import com.knuipalab.dsmp.dto.project.ProjectInviteRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectOustRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UnAuthorizedAccessException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UserEmailBadRequestException;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import com.knuipalab.dsmp.service.user.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ProjectServiceTest {

    @MockBean
    private MetaDataService metaDataService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProjectRepository projectRepository;

    String projectId1 = "54321";
    String projectId2 = "54322";

    public User createMockUser(){
        return User.builder()
                .name("mockUser")
                .email("mockUser@mockUser.com")
                .picture("mockUserImg")
                .build();
    }

    public User createMockVisitedUser(int num){
        return User.builder()
                .name("visitedUser"+num)
                .email("visit" + num + "@visit" +num +".com")
                .picture("visit"+num+"Img")
                .build();
    }

    public Project createMockProject(){

        User mockUser = createMockUser();

        Project project1 = Project.builder()
                .projectId(projectId1)
                .projectName("US")
                .creator(mockUser)
                .build();

        project1.invite(Arrays.asList(createMockVisitedUser(1),createMockVisitedUser(2)));

        return project1;
    }

    public List<Project> createMockProjectList(){

        User mockUser = createMockUser();

        Project project1 = Project.builder()
                .projectId(projectId1)
                .projectName("US")
                .creator(mockUser)
                .build();

        project1.invite(Arrays.asList(createMockVisitedUser(1),createMockVisitedUser(2)));

        Project project2 = Project.builder()
                .projectId(projectId2)
                .projectName("US")
                .creator(mockUser)
                .build();

        project2.invite(Arrays.asList(createMockVisitedUser(2),createMockVisitedUser(3)));

        List<Project> projectList = Arrays.asList(project1,project2);

        return projectList;
    }

    @Test
    @DisplayName("Find by Creator - Success")
    public void findByCreatorTest(){

        // given
        String _id = "507f191e810c19729de860ea";

        ObjectId creatorId = new ObjectId(_id);

        List<Project> mockedProjectList = createMockProjectList();

        // mocking
        given(projectRepository.findByCreator(creatorId))
                .willReturn(mockedProjectList);

        //when
        List<ProjectResponseDto> mockedProjectResponseDtoList =  projectRepository.findByCreator(creatorId)
                .stream()
                .map( project -> new ProjectResponseDto(project) )
                .collect(Collectors.toList());

        //then
        Assertions.assertEquals(projectId1,mockedProjectResponseDtoList.get(0).getProjectId());
        Assertions.assertEquals(projectId2,mockedProjectResponseDtoList.get(1).getProjectId());
        Assertions.assertEquals("mockUser",mockedProjectResponseDtoList.get(0).getCreator().getName());
        Assertions.assertEquals("mockUser",mockedProjectResponseDtoList.get(1).getCreator().getName());
    }

    @Test
    @DisplayName("Find Invited Project - Success")
    public void findInvitedProjectTest(){

        // given
        String _id = "507f191e810c19729de860ea"; // 2번 유저라 가정

        ObjectId userId = new ObjectId(_id);

        List<Project> mockedProjectList = createMockProjectList();

        // mocking
        given(projectRepository.findInvisitedProject(userId))
                .willReturn(mockedProjectList);

        //when
        List<ProjectResponseDto> mockedProjectResponseDtoList = projectRepository.findInvisitedProject(userId)
                .stream()
                .map( project -> new ProjectResponseDto(project) )
                .collect(Collectors.toList());

        //then
        Assertions.assertEquals(projectId1,mockedProjectResponseDtoList.get(0).getProjectId());
        Assertions.assertEquals(projectId2,mockedProjectResponseDtoList.get(1).getProjectId());
        Assertions.assertEquals("mockUser",mockedProjectResponseDtoList.get(0).getCreator().getName());
        Assertions.assertEquals("mockUser",mockedProjectResponseDtoList.get(1).getCreator().getName());
        Assertions.assertEquals("visitedUser2",mockedProjectResponseDtoList.get(0).getVisitor().get(1).getName()); //project1번의 visitor 1번 index에 visitedUser2가 있나 확인
        Assertions.assertEquals("visitedUser2",mockedProjectResponseDtoList.get(1).getVisitor().get(0).getName()); //project2번의 visitor 0번 index에 visitedUser2가 있나 확인
    }

    @Test
    @DisplayName("Find Project By Id - Success")
    public void findByIdTest(){

        // given
        Project mockedProject = createMockProject();

        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);

        //then
        Assertions.assertEquals(projectId1,optionalMockedProject.get().getProjectId());
        Assertions.assertEquals("mockUser",optionalMockedProject.get().getCreator().getName());

    }

    @Test
    @DisplayName("Insert Project - Success")
    public void insertTest(){

        //given
        String _id = "507f191e810c19729de860ea"; // 2번 유저라 가정
        ProjectRequestDto projectRequestDto = new ProjectRequestDto("NewName");
        User mockedUser = createMockUser();

        // mocking
        given(userRepository.findById(_id))
                .willReturn(Optional.ofNullable(mockedUser));

        //when
        Project project = new Project().builder()
                .projectName(projectRequestDto.getProjectName())
                .creator(userRepository.findById(_id).get())
                .build();

        projectRepository.save(project);

        //then
        Assertions.assertEquals("NewName",project.getProjectName());
        Assertions.assertEquals("mockUser",project.getCreator().getName());
        verify(projectRepository,times(1)).save(project);

    }

    @Test
    @DisplayName("Update Project - Success")
    public void updateTest(){

        // given
        Project mockedProject = createMockProject();
        String beforeUpdatedProjectName = mockedProject.getProjectName();
        ProjectRequestDto projectRequestDto = new ProjectRequestDto("UpdatedName");

        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);

        optionalMockedProject.get().update(projectRequestDto.getProjectName());

        projectRepository.save(optionalMockedProject.get());

        //then
        Assertions.assertNotEquals(beforeUpdatedProjectName,optionalMockedProject.get().getProjectName());
        Assertions.assertEquals("UpdatedName",optionalMockedProject.get().getProjectName());
        verify(projectRepository,times(1)).save(optionalMockedProject.get());

    }

    @Test
    @DisplayName("Delete Project By Id - Success")
    public void deleteByIdTest(){

        // given
        String userId = null;
        Project mockedProject = createMockProject();

        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);
        Project project = optionalMockedProject.get();

        if( project.getCreator().getUserId() != userId){ // null 이여야함
            throw new UnAuthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        metaDataService.deleteAllByProjectId(projectId1);
        projectRepository.delete(project);

        // then
        verify(metaDataService,times(1)).deleteAllByProjectId(projectId1);
        verify(projectRepository,times(1)).delete(project);
    }

    @Test
    @DisplayName("Invite Project By EmailList - Success")
    public void inviteTest() {

        // given
        String userId = null;

        Project mockedProject = createMockProject();

        User visitedUser3 = createMockVisitedUser(3);
        User visitedUser4 = createMockVisitedUser(4);

        ProjectInviteRequestDto projectInviteRequestDto = ProjectInviteRequestDto.builder()
                .emailList(Arrays.asList("visit3@visit3.com","visit4@visit4.com")).build();


        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        given(userService.findUserByEmail("visit3@visit3.com"))
                .willReturn(Optional.ofNullable(visitedUser3));

        given(userService.findUserByEmail("visit4@visit4.com"))
                .willReturn(Optional.ofNullable(visitedUser4));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);
        Project project = optionalMockedProject.get();

        if( project.getCreator().getUserId() != userId){ // null 이여야함
            throw new UnAuthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
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

        // then
        Assertions.assertEquals("visitedUser3",project.getVisitor().get(2).getName());
        Assertions.assertEquals("visitedUser4",project.getVisitor().get(3).getName());
        verify(projectRepository,times(1)).save(project);

    }

    @Test
    @DisplayName("Oust Project By EmailList - Success")
    public void oustByEmailListTest() {

        // given
        Project mockedProject = createMockProject();

        User visitedUser1 = createMockVisitedUser(1);
        User visitedUser2 = createMockVisitedUser(2);

        ProjectOustRequestDto projectOustRequestDto = ProjectOustRequestDto.builder()
                .emailList(Arrays.asList("visit1@visit1.com","visit2@visit2.com")).build();


        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        given(userService.findUserByEmail("visit1@visit1.com"))
                .willReturn(Optional.ofNullable(visitedUser1));

        given(userService.findUserByEmail("visit2@visit2.com"))
                .willReturn(Optional.ofNullable(visitedUser2));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);
        Project project = optionalMockedProject.get();


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

        // then
        Assertions.assertEquals(0,project.getVisitor().size());
        verify(projectRepository,times(1)).save(project);

    }

    @Test
    @DisplayName("Oust Project By LoginedUserEmail - Success")
    public void oust() {

        // given
        Project mockedProject = createMockProject();
        String loginedUserEmail = "visit1@visit1.com";

        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);
        Project project = optionalMockedProject.get();


        project.oust(loginedUserEmail);

        projectRepository.save(project);

        // then
        Assertions.assertEquals(1,project.getVisitor().size());
        Assertions.assertEquals(project.getVisitor().get(0).getName(),"visitedUser2");
        verify(projectRepository,times(1)).save(project);

    }





}
