package com.knuipalab.dsmp.controller.metadata.api;

import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.configuration.auth.SecurityConfig;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MetaDataApiController.class)
class MetaDataApiControllerTest {

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    private MetaDataService metaDataService;

    public String bodyStr = "{\n" +
            "   \"stored_dicom_id\": 145125,\n" +
            "    \"anonymized_id\": 1028011,\n" +
            "    \"age\": 53,\n" +
            "    \"modality\": \"MG\",\n" +
            "    \"manufacturer\": \"HOLOGIC, Inc.\",\n" +
            "    \"manufacturerModelName\": \"Lorad Selenia\",\n" +
            "    \"class non-pCR: 0 pCR: 1\": 0,\n" +
            "    \"left: 0 right: 1\": 1,\n" +
            "    \"ER\": 1,\n" +
            "    \"PR\": 1,\n" +
            "    \"HER2\": 1,\n" +
            "    \"non-IDC: 0\\nIDC: 1\": 1,\n" +
            "    \"compressionForce\": 173.5019\n" +
            "  }";

    public MetaData createTestData(){
        String metaId = "12345";
        String proId = "54321";
        Bson body = Document.parse(bodyStr);

        MetaData testData = MetaData.builder()
                .metadataId(metaId)
                .projectId(proId)
                .body(body)
                .build();

        return testData;
    }

    @WithMockUser
    @DisplayName("get by {projectId}")
    @Test
    void findByProjectidTest() throws Exception {
        //given
        MetaData testData = createTestData();
        List<MetaDataResponseDto> testDtoList = new ArrayList<MetaDataResponseDto>();
        testDtoList.add(new MetaDataResponseDto(testData));
        given(metaDataService.findByProjectId("54321"))
                .willReturn(testDtoList);

        mvc.perform(get("/api/MetaData/54321"))
                .andExpect(status().isOk()) // status 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //JSON 반환
                .andExpect(jsonPath("$[0].metadataId", is("12345")))
                .andExpect(jsonPath("$[0].projectId", is("54321")))
                .andExpect(jsonPath("$[0].body.age", is(53))) // body 확인
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("post by {projectID}")
    @Test
    void insertTest() throws Exception {
        mvc.perform(post("/api/MetaData/54321")
                        .content(bodyStr))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("put by {metadataId}")
    @Test
    void updateTest() throws Exception{
        mvc.perform(put("/api/MetaData/12345")
                .content("{\n" +
                        "   \"stored_dicom_id\": 283918,\n" +
                        "    \"anonymized_id\": 3389322,\n" +
                        "    \"age\": 77,\n" +
                        "    \"modality\": \"MG\",\n" +
                        "    \"manufacturer\": \"HOLOGIC, Inc.\",\n" +
                        "    \"manufacturerModelName\": \"Lorad Selenia\",\n" +
                        "    \"class non-pCR: 0 pCR: 1\": 0,\n" +
                        "    \"left: 0 right: 1\": 1,\n" +
                        "    \"ER\": 1,\n" +
                        "    \"PR\": 1,\n" +
                        "    \"HER2\": 1,\n" +
                        "    \"non-IDC: 0\\nIDC: 1\": 1,\n" +
                        "    \"compressionForce\": 173.5019\n" +
                        "  }"))
                .andExpect(status().isOk())
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("delete by {metadataId}")
    @Test
    void deleteByIdTest() throws Exception{
        mvc.perform(delete("/api/MetaData/12345"))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }
}

