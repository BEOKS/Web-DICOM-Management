package com.knuipalab.dsmp.service.machineLearning;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.knuipalab.dsmp.machineLearning.service.SampleType;
import com.knuipalab.dsmp.metadata.domain.MetaData;
import com.knuipalab.dsmp.metadata.domain.MetaDataRepository;
import com.knuipalab.dsmp.metadata.dto.MetaDataCreateAllRequestDto;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
public class AsyncMetaDataSamplerTest {

    @MockBean
    private MetaDataRepository metaDataRepository;

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

    @Test
    public void typeSampling() {

        // given
        String projectId = "54321";

        // mocking
       given(metaDataRepository.findByProjectId(projectId))
                .willReturn(createMockMetaDataList());
        //when
        List<MetaData> mockedMetaDataList = metaDataRepository.findByProjectId(projectId);

        final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_THREADS);

        int seq = 0;
        int chunk_size = 1000;
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for (List<MetaData> batch : Lists.partition(mockedMetaDataList,chunk_size)) {
            Future f = executor.submit(new updateThread(batch, seq));
            futures.add(f);
            seq += 1;
        }

        for (Future<Runnable> f : futures)
        {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        // then
        verify(metaDataRepository,times(mockedMetaDataList.size())).updateType(any(),any());

    }

    class updateThread implements Runnable {

        private List<MetaData> metaDataList;
        int seq;

        public updateThread(List<MetaData> metaDataList,int seq){
            this.metaDataList = metaDataList;
            this.seq = seq;
        }

        @Override
        public void run() {

            // mocking
            double trainPercent = 0.7 , validPercent = 0.2 , testPercent = 0.1 ;
            int metaDataListSize = metaDataList.size();
            int trainSize = (int)(metaDataListSize * trainPercent);
            int validSize = (int)(metaDataListSize * validPercent);
            int testSize = metaDataListSize - (trainSize+validSize);
            int randomValue;

            SampleType sampleType = null;
            int cnt = 0;
            while(trainSize!=0 || validSize!=0 || testSize!=0 ) {
                randomValue = (int)(Math.random() * 10);
                if( 0 <= randomValue && randomValue < 7 && trainSize!=0 ){
                    trainSize -= 1;
                    sampleType = SampleType.TRAIN;
                }
                else if ( randomValue < 9 && validSize!=0 ) {
                    validSize -= 1;
                    sampleType = SampleType.VALID;
                }
                else if ( randomValue < 10 && testSize!=0 ) {
                    testSize -= 1;
                    sampleType = SampleType.TEST;
                }
                else {
                    continue;
                }
                metaDataRepository.updateType(metaDataList.get(cnt).getMetadataId(),sampleType.getTypeString());
                cnt += 1;
            }

        }
    }

    @Test
    public void setMalignancyClassification() {

        // given
        String projectId = "54321";

        // mocking
        given(metaDataRepository.findByProjectId(projectId))
                .willReturn(createMockMetaDataList());
        //when
        List<MetaData> mockedMetaDataList = metaDataRepository.findByProjectId(projectId);

        List<MetaData> metaDataList = metaDataRepository.findByProjectId("54321");

        final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_THREADS);

        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for ( MetaData metadata : metaDataList ) {
            Future f = executor.submit(new getAndSetMalignancyClassificationThread(metadata.getMetadataId(),metadata.getImageNameFromBody()));
            futures.add(f);
        }
        // wait for all tasks to complete before continuing
        for (Future<Runnable> f : futures)
        {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service so that this thread can exit
        executor.shutdown();

        verify(metaDataRepository,times(mockedMetaDataList.size())).setMalignancyClassification(any(),any());
    }

    class getAndSetMalignancyClassificationThread implements Runnable {

        private String metadataId;
        private String imageName;

        public getAndSetMalignancyClassificationThread(String metadataId,String imageName){
            this.metadataId = metadataId;
            this.imageName = imageName;
        }

        @Override
        public void run() {
            String strJsonNode = "{\n" +
                    "   \"classification1\": 120.12,\n" +
                    "    \"classification2\": 50.8\n" +
                    "  }";
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=null;
            try {
                jsonNode = objectMapper.readTree(strJsonNode);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if(jsonNode == null) {
                throw new NullPointerException();
            }
            HashMap<String,Object> classificationSet = new HashMap<>();
            jsonNode.fields().forEachRemaining(
                    field -> { classificationSet.put(field.getKey(),field.getValue()); }
            );
            metaDataRepository.setMalignancyClassification(metadataId,classificationSet);
        }

    }

}
