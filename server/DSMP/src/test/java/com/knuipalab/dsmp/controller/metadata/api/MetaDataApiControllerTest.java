package com.knuipalab.dsmp.controller.metadata.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.dto.metadata.*;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import org.bson.Document;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String strBody = "{\n" +
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

    public String strBodyList = "[{\n" +
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
            "  },\n" +
            "  {\n" +
            "   \"stored_dicom_id\": 145126,\n" +
            "    \"anonymized_id\": 1028012,\n" +
            "    \"age\": 54,\n" +
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
            "  }]";

    public List<Document> convertToDocument(String strBodyList) {

        ObjectMapper mapper = new ObjectMapper();
        List<Document> bsonList = null;
        try {
            bsonList = mapper.readValue(strBodyList, new TypeReference<List<Document>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bsonList;
    }
    public List<MetaData> createMockMetaDataList(){
        List<MetaData> metaDataList = new ArrayList<>();
        String metadataId = "12345";
        String projectId = "54321";

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(projectId,convertToDocument(strBodyList));
        List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();

        for(Document body : bodyList) {
            MetaData metaData = new MetaData().builder()
                    .metadataId(metadataId)
                    .projectId(projectId)
                    .body(body).build();
            metaDataList.add(metaData);
            metadataId = String.valueOf((Integer.parseInt(metadataId)+1));
        }

        return metaDataList;
    }

    @WithMockUser
    @DisplayName("Find by ProjectId - Success")
    @Test
    void findByProjectIdTest() throws Exception {

        //given
        List<MetaData> metaDataList = createMockMetaDataList();

        List<MetaDataResponseDto> metaDataResponseDtoList = metaDataList.stream()
                .map(metaData -> new MetaDataResponseDto(metaData))
                .collect(Collectors.toList());

        //when
        given(metaDataService.findByProjectId("54321"))
                .willReturn(metaDataResponseDtoList);

        mvc.perform(get("/api/MetaData/54321"))
                .andExpect(status().isOk()) // status 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //JSON 반환
                .andExpect(jsonPath("$.status",is(200)))
                .andExpect(jsonPath("$.count",is(2)))
                .andExpect(jsonPath("$.body.[0].metadataId", is("12345")))
                .andExpect(jsonPath("$.body.[0].projectId", is("54321")))
                .andExpect(jsonPath("$.body.[0].body.age", is(53))) // body 확인
                .andExpect(jsonPath("$.body.[1].metadataId", is("12346")))
                .andExpect(jsonPath("$.body.[1].projectId", is("54321")))
                .andExpect(jsonPath("$.body.[1].body.age", is(54))) // body 확인
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Insert by ProjectId - Success")
    @Test
    void insertTest() throws Exception {

        String projectId = "54321";

        //Dto
        MetaDataCreateRequestDto metaDataCreateRequestDto = new MetaDataCreateRequestDto(projectId,Document.parse(strBody));

        mvc.perform(post("/api/MetaData/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(strBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Insert all by MetaData List - Success")
    @Test
    void insertAllByMetaDataListTest() throws Exception {

        String projectId = "54321";

        //Dto
        List<Document> documentList = convertToDocument(strBodyList);
        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(projectId,convertToDocument(strBodyList));

        mvc.perform(post("/api/MetaDataList/insert/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(strBodyList))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
        ;
    }

    @WithMockUser
    @DisplayName("Delete All By MetaData Id List - Success")
    @Test
    void deleteAllByMetaDataIdListTest() throws Exception {

        String projectId = "54321";

        List<String> metadataIdList = List.of("12345","23456");

        //Dto
        MetaDataDeleteAllRequestDto metaDataDeleteAllRequestDto = new MetaDataDeleteAllRequestDto(projectId,metadataIdList);

        mvc.perform(post("/api/MetaDataList/delete/54321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"12345\",\"12346\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
        ;
    }


    @WithMockUser
    @DisplayName("Update By MetadataId - Success")
    @Test
    void updateTest() throws Exception{

        String metadataId = "12345";
        String updatedStrBody = "{\n" +
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
                "  }";

        //Dto
        MetaDataUpdateRequestDto metaDataUpdateRequestDto = new MetaDataUpdateRequestDto(metadataId,Document.parse(updatedStrBody));

        mvc.perform(put("/api/MetaData/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedStrBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status",is(200)))
                        .andDo(print())
                ;
    }

    @WithMockUser
    @DisplayName("Delete by MetadataId - Success")
    @Test
    void deleteByIdTest() throws Exception{
        mvc.perform(delete("/api/MetaData/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
        ;
    }

}

