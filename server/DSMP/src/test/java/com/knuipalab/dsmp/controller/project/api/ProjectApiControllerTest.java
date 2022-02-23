package com.knuipalab.dsmp.controller.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.configuration.auth.SecurityConfig;
import com.knuipalab.dsmp.controller.metadata.api.MetaDataApiController;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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


//    @DisplayName("get all")
//    @Test
//    void findAllTest() throws Exception{
//        //given
//        Project project = Project.builder()
//                .projectId("54321")
//                .projectName("US")
//                .build();
//        List<ProjectResponseDto> projectResponseDtoList = new ArrayList<>();
//        projectResponseDtoList.add(new ProjectResponseDto(project));
//        given(projectService.findAll())
//                .willReturn(projectResponseDtoList);
//
//        mockMvc.perform(get("/api/Project"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].projectId", is("54321")))
//                .andExpect(jsonPath("$[0].projectName", is("US")))
//                .andDo(print())
//                ;
//    }


    @WithMockUser
    @DisplayName("post Project")
    @Test
    void insertTest() throws Exception{
        //given
        ProjectRequestDto projectRequestDto = new ProjectRequestDto("new");

        mockMvc.perform(post("/api/Project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("put by {projectId}")
    @Test
    void updateTest() throws Exception{
        //given
        ProjectRequestDto projectRequestDto = new ProjectRequestDto("update");
        mockMvc.perform(put("/api/Project/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(projectRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("delete by {projectId}")
    @Test
    void deleteTest() throws Exception{
        mockMvc.perform(delete("/api/Project/54321"))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }
}
