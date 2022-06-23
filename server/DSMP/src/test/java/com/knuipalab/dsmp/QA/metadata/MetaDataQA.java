package com.knuipalab.dsmp.QA.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import com.knuipalab.dsmp.metadata.CustomizedMetaDataRepository;
import com.knuipalab.dsmp.metadata.MetaData;
import com.knuipalab.dsmp.metadata.MetaDataRepository;
import com.knuipalab.dsmp.project.Project;
import com.knuipalab.dsmp.user.User;
import com.knuipalab.dsmp.metadata.MetaDataCreateAllRequestDto;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {  // 실제 MongoDB에 접근하여 test하기 위해, exclude 시킴. default profile QA로 설정 해야 함
        EmbeddedMongoAutoConfiguration.class
})
@Profile("QA") // QA 에서만 실제 몽고 db에 접근
public class MetaDataQA {

    @SpyBean
    MetaDataRepository metaDataRepository;

    @SpyBean
    CustomizedMetaDataRepository customizedMetaDataRepository;

    Logger log = (Logger) LoggerFactory.getLogger(MetaDataQA.class);

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

    public String createMockStrBodyList(){

        long beforeTime = System.currentTimeMillis();

        long METADATA_SIZE = 200;

        Faker faker = new Faker();
        StringBuilder strBodyList = new StringBuilder();

        strBodyList.append("[\n");
        for(long i=0; i<METADATA_SIZE ; i ++ ){
            strBodyList.append(" {\n");
            strBodyList.append("   \"stored_dicom_id\": " + faker.number().numberBetween(111111,9999999)+",\n");
            strBodyList.append("   \"anonymized_id\": " + faker.number().numberBetween(11111111,99999999)+",\n");
            strBodyList.append("   \"image_name\": " + "\"" + String.format("%s_%s","a",faker.number().numberBetween(11111111,99999999)) +"\""+",\n");
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
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산

        log.info("createMockStrBodyList 실행 시간(ms) : "+secDiffTime);

        return strBodyList.toString();
    }


    @Profile("QA")
    @Test
    public void saveQA(){

        Project mockProject = createMockProject("54321");

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(mockProject.getProjectId(),convertToDocument(createMockStrBodyList()));

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

        metaDataList.stream().forEach( metaData -> metaDataRepository.save(metaData));

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산

        log.info("save QA 실행 시간(m) : "+secDiffTime);

    }


    @Profile("QA")
    @Test
    public void saveAllQA(){

        Project mockProject = createMockProject("54321");

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(mockProject.getProjectId(),convertToDocument(createMockStrBodyList()));

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

        int chunk_size = 1000;
        for (List<MetaData> batch : Lists.partition(metaDataList,chunk_size)) {
            metaDataRepository.saveAll(batch);
        }


        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산

        log.info("saveAll QA 실행 시간(ms) : "+secDiffTime);

    }

    @Profile("QA")
    @Test
    public void findAllQA(){
        long beforeTime = System.currentTimeMillis();

        List<MetaData> metaDataList = metaDataRepository.findAll();

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        log.info("findAll 개수 : "+metaDataList.size());
        log.info("findAll QA 실행 시간(ms) : "+secDiffTime);
    }

    @Profile("QA")
    @Test
    public void findByProjectIdWithPagingAndFiltering() {
        long beforeTime = System.currentTimeMillis();
        HashMap<String,Object> parmMap = new HashMap<>();
        Page<MetaData> metaDataPage = customizedMetaDataRepository.findByProjectIdWithPagingAndFiltering("54321",11123,20,parmMap);
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        log.info("findByProjectIdWithPagingAndFiltering 개수 : "+metaDataPage.getContent().size());
        log.info("findByProjectIdWithPagingAndFiltering QA 실행 시간(ms) : "+secDiffTime);
    }


}
