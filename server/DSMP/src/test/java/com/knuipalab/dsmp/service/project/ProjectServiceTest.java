package com.knuipalab.dsmp.service.project;


import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.project.ProjectRepository;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.domain.user.UserRepository;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import com.knuipalab.dsmp.service.user.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

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
                .name("testName")
                .email("test@test.com")
                .picture("testImg")
                .build();
    }

    public Project createMockProject(){

        User mockUser = createMockUser();

        Project project1 = Project.builder()
                .projectId(projectId1)
                .projectName("US")
                .creator(mockUser)
                .build();

        return project1;
    }

    public List<Project> createMockProjectList(){

        User mockUser = createMockUser();

        Project project1 = Project.builder()
                .projectId(projectId1)
                .projectName("US")
                .creator(mockUser)
                .build();

        Project project2 = Project.builder()
                .projectId(projectId2)
                .projectName("US")
                .creator(mockUser)
                .build();

        List<Project> projectList = Arrays.asList(project1,project2);

        return projectList;
    }

    @Test
    @DisplayName("Find by Creator")
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
        Assertions.assertEquals(mockedProjectResponseDtoList.get(0).getProjectId(),projectId1);
        Assertions.assertEquals(mockedProjectResponseDtoList.get(1).getProjectId(),projectId2);
        Assertions.assertEquals(mockedProjectResponseDtoList.get(0).getCreator().getName(),"testName");
        Assertions.assertEquals(mockedProjectResponseDtoList.get(1).getCreator().getName(),"testName");
    }

    @Test
    @DisplayName("Find Invited Project")
    public void findInvitedProjectTest(){

        // given
        String _id = "507f191e810c19729de860ea";

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
        Assertions.assertEquals(mockedProjectResponseDtoList.get(0).getProjectId(),projectId1);
        Assertions.assertEquals(mockedProjectResponseDtoList.get(1).getProjectId(),projectId2);
        Assertions.assertEquals(mockedProjectResponseDtoList.get(0).getCreator().getName(),"testName");
        Assertions.assertEquals(mockedProjectResponseDtoList.get(1).getCreator().getName(),"testName");
    }

    @Test
    @DisplayName("Find Project By Id")
    public void findById(){

        // given
        String _id = "507f191e810c19729de860ea";

        ObjectId userId = new ObjectId(_id);

        Project mockedProject = createMockProject();

        // mocking
        given(projectRepository.findById(projectId1))
                .willReturn(Optional.ofNullable(mockedProject));

        //when
        Optional<Project> optionalMockedProject = projectRepository.findById(projectId1);

        //then
        Assertions.assertEquals(optionalMockedProject.get().getProjectId(),projectId1);
        Assertions.assertEquals(optionalMockedProject.get().getCreator().getName(),"testName");

    }

}
