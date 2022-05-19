package com.knuipalab.dsmp.service.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.metadata.*;
import com.knuipalab.dsmp.project.Project;
import com.knuipalab.dsmp.user.User;
import com.knuipalab.dsmp.project.ProjectResponseDto;
import com.knuipalab.dsmp.http.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.http.httpResponse.error.handler.exception.MetaDataNotFoundException;
import com.knuipalab.dsmp.project.ProjectService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class MetaDataServiceTest {

    @MockBean
    private MetaDataRepository metaDataRepository;

    @MockBean
    private ProjectService projectService;

    public String strBody = "{\n" +
            "   \"stored_dicom_id\": 145125,\n" +
            "    \"anonymized_id\": 1028011,\n" +
            "    \"image_name\": " + "\"a_123456\""+",\n" +
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
            "    \"stored_dicom_id\": 145125,\n" +
            "    \"anonymized_id\": 1028011,\n" +
            "    \"image_name\": " + "\"a_123456\""+",\n" +
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
            "    \"stored_dicom_id\": 145126,\n" +
            "    \"anonymized_id\": 1028012,\n" +
            "    \"image_name\": " + "\"a_123457\""+",\n" +
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
        String metaId = "12345";
        String proId = "54321";

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(proId,convertToDocument(strBodyList));
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
                .name("mockUser")
                .email("mockUser@mockUser.com")
                .picture("mockUserImg")
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
    @DisplayName("Find by ProjectId - Success")
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
    @DisplayName("Find by ProjectId with Paging - Success")
    public void findByProjectIdWithPaging() {

        // given
        String projectId = "54321";
        int page = 0;
        int size = 20;
        List<MetaData> mockedMetaDataList = createMockMetaDataList();

        HashMap<String,Object> parmMap = new HashMap<String,Object>();

        Pageable pageable = PageRequest.of(0,2, Sort.unsorted());
        Page<MetaData> metaDataPage = PageableExecutionUtils.getPage(
                mockedMetaDataList,
                pageable,
                () -> 5
        );

        // mocking
        given(metaDataRepository.findByProjectIdWithPaging(projectId,page,size,parmMap))
                .willReturn(metaDataPage);

        //when
        Page<MetaData> mockedMetaDataPage = metaDataRepository.findByProjectIdWithPaging(projectId,page,size,parmMap);

        //then
        Assertions.assertEquals(mockedMetaDataPage.getTotalElements(),5);
        Assertions.assertEquals(mockedMetaDataPage.getTotalPages(),3);
        Assertions.assertEquals(mockedMetaDataPage.getContent().size(),mockedMetaDataList.size());


    }

    @Test
    @DisplayName("Insert MetaData - Success")
    public void insertTest(){

        // given
        String projectId = "54321";

        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        MetaDataCreateRequestDto metaDataCreateRequestDto = new MetaDataCreateRequestDto(projectId,Document.parse(strBody));

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
        Assertions.assertNull(mockedMetaData.getMetadataId());
        Assertions.assertEquals(mockedMetaData.getProjectId(),projectId);

    }

    @Test
    @DisplayName("Insert All MetaData By MetaData List - Success")
    void insertAllByMetaDataListTest() throws Exception{

        // given
        String projectId = "54321";

        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(projectId,convertToDocument(strBodyList));

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
        projectService.findById(metaDataCreateAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        List<MetaData> mockedMetaDataList = metaDataRepository.insert(metaDataList);

        // then
        Assertions.assertEquals(mockedMetaDataList.get(0).getProjectId(),projectId);
        Assertions.assertEquals(mockedMetaDataList.get(1).getProjectId(),projectId);
        Assertions.assertNull(mockedMetaDataList.get(0).getMetadataId());
        Assertions.assertNull(mockedMetaDataList.get(1).getMetadataId());
        Assertions.assertEquals(mockedMetaDataList.get(0).getPatientIdFromBody(),"1028011");
        Assertions.assertEquals(mockedMetaDataList.get(1).getPatientIdFromBody(),"1028012");
    }

    @Test
    @DisplayName("Delete All MetaData By MetaData Id List - Success")
    void deleteAllByMetaDataIdListTest() throws Exception{

        // given
        String projectId = "54321";

        List<String> metadataIdList = List.of("12345","12346");

        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        MetaDataDeleteAllRequestDto metaDataDeleteAllRequestDto = new MetaDataDeleteAllRequestDto(projectId,metadataIdList);

        List<MetaData> metaDataList = createMockMetaDataList();

        // mocking
        given(projectService.findById(metaDataDeleteAllRequestDto.getProjectId()))
                .willReturn(projectResponseDto);

        given(metaDataRepository.findById(metadataIdList.get(0)))
                .willReturn(Optional.ofNullable(metaDataList.get(0)));

        given(metaDataRepository.findById(metadataIdList.get(1)))
                .willReturn(Optional.ofNullable(metaDataList.get(1)));


        // when
        projectService.findById(metaDataDeleteAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        List<MetaData> deletedMetaDataList = metaDataDeleteAllRequestDto.getMetadataIdList().stream()
                .map( metadataId ->  metaDataRepository.findById(metadataId).orElseThrow(()-> new MetaDataNotFoundException(ErrorCode.METADATA_NOT_FOUND)))
                .collect(Collectors.toList());

        metaDataRepository.deleteAll(deletedMetaDataList);
        // then
        Assertions.assertEquals(deletedMetaDataList.get(0).getMetadataId(),metaDataDeleteAllRequestDto.getMetadataIdList().get(0));
        Assertions.assertEquals(deletedMetaDataList.get(1).getMetadataId(),metaDataDeleteAllRequestDto.getMetadataIdList().get(1));
        verify(metaDataRepository,times(1)).deleteAll(deletedMetaDataList);
    }

    @Test
    @DisplayName("Update MetaData - Success")
    public void updateTest(){

        // given
        String metadataId = "12345";
        String updatedStrBody = "{\n" +
                "    \"stored_dicom_id\": 283918,\n" +
                "    \"anonymized_id\": 3389322,\n" +
                "    \"image_name\": " + "\"a_123456\""+",\n" +
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

        MetaDataUpdateRequestDto metaDataUpdateRequestDto = new MetaDataUpdateRequestDto(metadataId,Document.parse(updatedStrBody));
        MetaData mockedMetaData = createMockMetaData();

        // mocking
        given(metaDataRepository.findById(metadataId))
                .willReturn(Optional.of(mockedMetaData));

        //when
        Optional<MetaData> OptionalMetaData = metaDataRepository.findById(metadataId);

        MetaData metaData = OptionalMetaData.get();
        metaData.update(metaDataUpdateRequestDto.getBody());

        metaDataRepository.save(metaData);

        //then
        Assertions.assertEquals(metaData.getPatientIdFromBody(),"3389322");
        verify(metaDataRepository,times(1)).save(metaData);
    }

    @Test
    @DisplayName("Delete MetaData By Id - Success")
    public void deleteByIdTest(){
        // given
        String metadataId = "12345";
        MetaData mockedMetaData = createMockMetaData();

        // mocking
        given(metaDataRepository.findById(metadataId))
                .willReturn(Optional.ofNullable(mockedMetaData));

        //when
        Optional<MetaData> optionalMockedMetaData = metaDataRepository.findById(metadataId);
        metaDataRepository.delete(optionalMockedMetaData.get());

        //then
        verify(metaDataRepository,times(1)).delete(optionalMockedMetaData.get());
    }

    @Test
    @DisplayName("Delete All MetaData By ProjectId - Success")
    public void deleteAllByProjectIdTest(){
        // given
        String projectId = "54321";
        ProjectResponseDto projectResponseDto = createMockProjectResponseDto();

        // mocking
        given(projectService.findById(projectId))
                .willReturn(projectResponseDto);

        //when
        given(projectService.findById(projectId))
                .willReturn(projectResponseDto);

        metaDataRepository.deleteAllByProjectId(projectId);

        //then
        verify(metaDataRepository,times(1)).deleteAllByProjectId(projectId);
    }
}
