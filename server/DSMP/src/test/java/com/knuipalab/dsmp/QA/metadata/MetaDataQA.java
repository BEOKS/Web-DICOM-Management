package com.knuipalab.dsmp.QA.metadata;

import com.github.javafaker.Faker;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateAllRequestDto;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {  // 실제 MongoDB에 접근하여 test하기 위해, exclude 시킴. default profile QA로 설정 해야 함
        EmbeddedMongoAutoConfiguration.class
})
public class MetaDataQA {

    @SpyBean
    MetaDataRepository metaDataRepository;

    public User createMockUser(){
        return User.builder()
                .name("mockUser")
                .email("mockUser@mockUser.com")
                .picture("mockUserImg")
                .build();
    }

    public Project createMockProject(String projectId){

        User mockUser = createMockUser();

        Project project1 = Project.builder()
                .projectId(projectId)
                .projectName("US")
                .creator(mockUser)
                .build();

        return project1;
    }

    public String createMockStrBodyList(){

        long beforeTime = System.currentTimeMillis();

        long METADATA_SIZE = 1000000;

        Faker faker = new Faker();
        StringBuilder strBodyList = new StringBuilder();

        strBodyList.append("[\n");
        for(long i=0; i<METADATA_SIZE ; i ++ ){
            strBodyList.append(" {\n");
            strBodyList.append("   \"stored_dicom_id\": " + faker.number().numberBetween(111111,9999999)+",\n");
            strBodyList.append("   \"anonymized_id\": " + faker.number().numberBetween(11111111,99999999)+",\n");
            strBodyList.append("   \"age\": " + faker.number().numberBetween(20,80) +",\n");
            strBodyList.append("   \"modality\": \"MG\",\n");
            strBodyList.append("   \"manufacturer\": " + "\"" + faker.company().name()+"\""+",\n");
            strBodyList.append("   \"class non-pCR: 0 pCR: 1\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"left: 0 right: 1\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"ER\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"PR\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"HER2\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"non-IDC: 0\\nIDC: 1\": " + faker.number().numberBetween(0,2)+",\n");
            strBodyList.append("   \"compressionForce\": " + 173.5019 +"\n");
            strBodyList.append(" },\n");
        }
        strBodyList.deleteCharAt( strBodyList.length() - 2 );
        strBodyList.append("]");

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산

        System.out.println("createMockStrBodyList 실행 시간(m) : "+secDiffTime);

        return strBodyList.toString();
    }

    @Test
    public void insertAllQA(){

        Project mockProject = createMockProject("54321");

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(mockProject.getProjectId(),createMockStrBodyList());

        long beforeTime = System.currentTimeMillis();

        List<Document> bodyList = metaDataCreateAllRequestDto.getBodyList();

        String projectId = metaDataCreateAllRequestDto.getProjectId();

        List<MetaData> metaDataList = new ArrayList<>();

        for(Document body : bodyList){
            MetaData metaData = new MetaData().builder()
                    .projectId(projectId)
                    .body(body).build();
            metaDataList.add(metaData);
        }

        metaDataRepository.insert(metaDataList);

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
        System.out.println("insertAllQA 실행 시간(m) : "+secDiffTime);

    }

}
