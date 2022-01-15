package com.knuipalab.dsmp.controller.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectApiController.class)
public class ProjectApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @DisplayName("get all")
    @Test
    void findAllTest() throws Exception{
        //given
        Project project = Project.builder()
                .projectId("54321")
                .projectName("US")
                .build();
        List<ProjectResponseDto> projectResponseDtoList = new ArrayList<>();
        projectResponseDtoList.add(new ProjectResponseDto(project));
        given(projectService.findAll())
                .willReturn(projectResponseDtoList);

        mockMvc.perform(get("/api/Project"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].projectId", is("54321")))
                .andExpect(jsonPath("$[0].projectName", is("US")))
                .andDo(print())
                ;
    }

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

    @DisplayName("delete by {projectId}")
    @Test
    void deleteTest() throws Exception{
        mockMvc.perform(delete("/api/Project/54321"))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }
}
