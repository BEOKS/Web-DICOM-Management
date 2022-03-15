package com.knuipalab.dsmp.controller.metadata.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateAllRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import org.bson.Document;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MetaDataApiController.class)
public class MetaDataAPIControllerLargeDataTest {
    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private MetaDataService metaDataService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
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
    private final String META_DATA_PATH="com/resources/MetaData/metadata.json";
    public List<MetaDataResponseDto> loadMetaDataResponseDtoList(){
        List<MetaDataResponseDto> metaDataResponseDtoList=null;
        if(getClass().getResource(META_DATA_PATH)==null){
            //create file
            System.out.println("cannot find test files in "+META_DATA_PATH+", create metadata.json file");
            try {
                String fullPath=getClass().getClassLoader().getResource(".").getPath()+META_DATA_PATH;
                fullPath=fullPath.substring(1);
                System.out.println(fullPath);
                Path newFilePath=Files.createFile(Paths.get(fullPath));
                System.out.println("create test file : " +newFilePath);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return metaDataResponseDtoList;
    }
    @WithMockUser
    @DisplayName("Upload Large Metadata")
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void uploadLargeMetadata() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader.getResource("."));
        loadMetaDataResponseDtoList();
    }

}
