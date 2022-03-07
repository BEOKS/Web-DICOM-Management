package com.knuipalab.dsmp.controller.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.dto.project.ProjectInviteRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectOustRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProjectApiController.class)
public class ProjectApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    ProjectService projectService;


    public User createMockUser(){
        return User.builder()
                .name("mockUser")
                .email("mockUser@mockUser.com")
                .picture("mockUserImg")
                .build();
    }

    public List<ProjectResponseDto> createMockProjectResponseDtoList(){

        User mockUser = createMockUser();

        Project project = Project.builder()
                .projectId("54321")
                .projectName("US")
                .creator(mockUser)
                .build();

        List<ProjectResponseDto> projectResponseDtoList = Arrays.asList(new ProjectResponseDto(project));

        return projectResponseDtoList;
    }

    @WithMockUser
    @DisplayName("Find by Creator - Success")
    @Test
    void findByCreatorTest() throws Exception{

        //given
        given(projectService.findByCreator())
                .willReturn(createMockProjectResponseDtoList());

        mockMvc.perform(get("/api/Project"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.[0].projectId", is("54321")))
                .andExpect(jsonPath("$.body.[0].projectName", is("US")))
                .andExpect(jsonPath("$.body.[0].creator.name",is("mockUser")))
                .andExpect(jsonPath("$.body.[0].creator.email",is("mockUser@mockUser.com")))
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Find Invisited Project - Success")
    @Test
    void findInvisitedProject() throws Exception{

        //given
        given(projectService.findInvitedProject())
                .willReturn(createMockProjectResponseDtoList());

        mockMvc.perform(get("/api/Project/invited"))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.[0].projectId", is("54321")))
                .andExpect(jsonPath("$.body.[0].projectName", is("US")))
                .andExpect(jsonPath("$.body.[0].creator.name",is("mockUser")))
                .andExpect(jsonPath("$.body.[0].creator.email",is("mockUser@mockUser.com")))
                .andDo(print())
        ;


    }


    @WithMockUser
    @DisplayName("Insert Project - Success")
    @Test
    void insertTest() throws Exception{
        //given
        String content = "{ \"projectName\" : \"insertedName\" }";

        //Dto
        ProjectRequestDto projectRequestDto = new ObjectMapper().readValue(content,ProjectRequestDto.class);

        mockMvc.perform(post("/api/Project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectRequestDto)))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Update by ProjectId - Success")
    @Test
    void updateTest() throws Exception{

        //given
        String content = "{ \"projectName\" : \"updatedName\" }";

        //Dto
        ProjectRequestDto projectRequestDto = new ObjectMapper().readValue(content,ProjectRequestDto.class);

        mockMvc.perform(put("/api/Project/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectRequestDto)))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Delete by ProjectId - Success")
    @Test
    void deleteTest() throws Exception{
        mockMvc.perform(delete("/api/Project/54321"))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Invite Project by emailList - Success")
    @Test
    void inviteTest() throws Exception{
        //given
        List<String> emailList = Arrays.asList("a@a.com","b@b.com");
        String emailListStr = "[\"a@a.com\",\"b@b.com\"]";

        //Dto
        ProjectInviteRequestDto projectInviteRequestDto = new ProjectInviteRequestDto("54321",emailList);

        mockMvc.perform(put("/api/Project/invite/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailListStr))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @WithMockUser
    @DisplayName("Oust Project - Success")
    @Test
    void oustTest() throws Exception{
        mockMvc.perform(put("/api/Project/oust/54321"))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @WithMockUser
    @DisplayName("Oust Project by emailList - Success")
    @Test
    void oustListTest() throws Exception{

        //given
        List<String> emailList = Arrays.asList("a@a.com","b@b.com");
        String emailListStr = "[\"a@a.com\",\"b@b.com\"]";
        //Dto
        ProjectOustRequestDto projectOustRequestDto = new ProjectOustRequestDto("54321",emailList);

        mockMvc.perform(put("/api/Project/oust/list/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailListStr))
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }


}
