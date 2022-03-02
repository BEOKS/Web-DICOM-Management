package com.knuipalab.dsmp.service.metadata;

import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateAllRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataUpdateRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class MetaDataServiceTest {

    @MockBean
    private MetaDataRepository metaDataRepository;

    @MockBean
    private ProjectService projectService;

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

    public MetaData createMockMetaData(){
        String metaId = "12345";
        String proId = "54321";
        Bson body = Document.parse(strBody);

        MetaData metaData = MetaData.builder()
                .metadataId(metaId)
                .projectId(proId)
                .body(body)
                .build();

        return metaData;
    }
    
    public List<MetaData> createMockMetaDataList(){
        List<MetaData> metaDataList = new ArrayList<>();
        String metaId = "12345";
        String proId = "54321";

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(proId,strBodyList);
        List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();
        String projectId = metaDataCreateAllRequestDto.getProjectId();

        for(Document body : bodyList) {
            MetaData metaData = new MetaData().builder()
                    .metadataId(metaId)
                    .projectId(projectId)
                    .body(body).build();
            metaDataList.add(metaData);
            metaId = String.valueOf((Integer.parseInt(metaId)+1));
        }

        return metaDataList;
    }

    public User createMockUser(){
        return User.builder()
                .name("testName")
                .email("test@test.com")
                .picture("testImg")
                .build();
    }

    public ProjectResponseDto createMockProjectResponseDto(){

        User mockUser = createMockUser();

        Project project = Project.builder()
                .projectId("54321")
                .projectName("US")
                .creator(mockUser)
                .build();

        return new ProjectResponseDto(project);
    }

    @Test
    @DisplayName("Find by ProjectId")
    public void findByProjectIdTest() {

        // given
        String projectId = "54321";
        List<MetaData> mockedMetaDataList = createMockMetaDataList();

        // mocking
        given(metaDataRepository.findByProjectId(projectId))
                .willReturn(mockedMetaDataList);

        //when
        List<MetaDataResponseDto> mockedMetaDataResponseDtoList = metaDataRepository.findByProjectId(projectId)
                .stream()
                .map( metaData -> new MetaDataResponseDto(metaData) )
                .collect(Collectors.toList());

        //then
        Assertions.assertEquals(mockedMetaDataResponseDtoList.get(0).getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaDataResponseDtoList.get(0).getMetadataId(),"12345");
        Assertions.assertEquals(mockedMetaDataResponseDtoList.get(1).getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaDataResponseDtoList.get(1).getMetadataId(),"12346");

    }

    @Test
    @DisplayName("Insert MetaData")
    public void insertTest(){

        // given
        String projectId = "54321";

        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        MetaDataCreateRequestDto metaDataCreateRequestDto = new MetaDataCreateRequestDto(projectId,strBody);

        MetaData metaData = new MetaData().builder()
                .projectId(metaDataCreateRequestDto.getProjectId())
                .body(metaDataCreateRequestDto.getBody()).build();

        // mocking
        given(projectService.findById(metaDataCreateRequestDto.getProjectId()))
                .willReturn(projectResponseDto);

        given(metaDataRepository.save(metaData))
                .willReturn(metaData);

        //when
        ProjectResponseDto mockedProjectResponseDto = projectService.findById(metaDataCreateRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        MetaData mockedMetaData = metaDataRepository.save(metaData); // 저장

        //then
        Assertions.assertEquals(mockedProjectResponseDto.getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaData.getMetadataId(),null);
        Assertions.assertEquals(mockedMetaData.getProjectId(),projectId);

    }

    @Test
    @DisplayName("Insert All MetaData")
    void insertAllTest() throws Exception{

        // given
        String projectId = "54321";

        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(projectId,strBodyList);

        List<MetaData> metaDataList = new ArrayList<>();

        List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();

        for(Document body : bodyList){
            MetaData metaData = new MetaData().builder()
                    .projectId(projectId)
                    .body(body).build();
            metaDataList.add(metaData);
        }

        // mocking
        given(projectService.findById(metaDataCreateAllRequestDto.getProjectId()))
                .willReturn(projectResponseDto);

        given(metaDataRepository.insert(metaDataList))
                .willReturn(metaDataList);

        // when
        ProjectResponseDto mockedProjectResponseDto = projectService.findById(metaDataCreateAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        projectService.findById(metaDataCreateAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        List<MetaData> mockedMetaDataList = metaDataRepository.insert(metaDataList);

        // then
        Assertions.assertEquals(mockedMetaDataList.get(0).getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaDataList.get(1).getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaDataList.get(0).getMetadataId(),null);
        Assertions.assertEquals(mockedMetaDataList.get(1).getMetadataId(),null);
        Assertions.assertEquals(mockedMetaDataList.get(0).getPatientIdFromBody(),"1028011");
        Assertions.assertEquals(mockedMetaDataList.get(1).getPatientIdFromBody(),"1028012");
    }

    @Test
    @DisplayName("Update MetaData")
    public void update(){

        // given
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

        MetaDataUpdateRequestDto metaDataUpdateRequestDto = new MetaDataUpdateRequestDto(updatedStrBody);
        MetaData mockedMetaData = createMockMetaData();

        // mocking
        given(metaDataRepository.findById(metadataId))
                .willReturn(Optional.of(mockedMetaData));

        //when
        Optional<MetaData> OptionalMetaData = metaDataRepository.findById(metadataId);

        MetaData metaData = OptionalMetaData.get();
        metaData.update(metaDataUpdateRequestDto.getBody());

        //then
        Assertions.assertEquals(metaData.getPatientIdFromBody(),"3389322");

    }
}
