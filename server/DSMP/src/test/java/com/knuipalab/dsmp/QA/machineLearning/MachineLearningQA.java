package com.knuipalab.dsmp.QA.machineLearning;

import com.google.common.collect.Lists;
import com.knuipalab.dsmp.QA.metadata.MetaDataQA;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.service.machineLearning.MLType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.util.BsonUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {  // 실제 MongoDB에 접근하여 test하기 위해, exclude 시킴. default profile QA로 설정 해야 함
        EmbeddedMongoAutoConfiguration.class
})
@Profile("QA") // QA 에서만 실제 몽고 db에 접근
public class MachineLearningQA {
    @SpyBean
    MetaDataRepository metaDataRepository;

    Logger log = (Logger) LoggerFactory.getLogger(MetaDataQA.class);

    @Profile("QA")
    @Test
    public void samplingQA() throws InterruptedException {

        String projectId = "54321";

        long sec = System.currentTimeMillis();

        List<MetaData> metaDataList = metaDataRepository.findByProjectId(projectId);

        sec = System.currentTimeMillis() - sec ; // 코드 실행 후에 시간 받아오기

        log.info("sampling - findByProjectId 실행 시간(m) : " + sec);

        sec = System.currentTimeMillis();

        ArrayList<Thread> threads = new ArrayList<>();

        final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_THREADS);

        int seq = 0;
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for (List<MetaData> batch : Lists.partition(metaDataList,1000)) {
            Future f = executor.submit(new updateThread(batch, seq));
            futures.add(f);
            seq += 1;
        }
       // wait for all tasks to complete before continuing
        for (Future<Runnable> f : futures)
        {
            try {
                f.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service so that this thread can exit
        executor.shutdown();


        sec = System.currentTimeMillis() - sec ; // 코드 실행 후에 시간 받아오기

        log.info("sampling - 전체 스레드 실행 시간(m) : "+sec);

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

            log.info("Thread " +seq+ "start! ");
            double trainPercent = 0.7 , validPercent = 0.2 , testPercent = 0.1 ;
            int metaDataListSize = metaDataList.size();
            int trainSize = (int)(metaDataListSize * trainPercent);
            int validSize = (int)(metaDataListSize * validPercent);
            int testSize = metaDataListSize - (trainSize+validSize);
            int randomValue;
            int mod = 3;

            long sec = System.currentTimeMillis();

            for(int i =0 ; i < metaDataList.size() ; i++ ){
                MetaData metaData = metaDataList.get(i);
                randomValue = (int)(Math.random() * 10);
                switch (mod) {
                    case 3 : {
                        if (randomValue%3 == 0 && 0 < trainSize ){
                            trainSize -= 1;
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.TRAIN.getTypeString());
                            break;
                        }
                        else if(randomValue%3 == 1 && 0 < validSize) {
                            validSize -= 1;
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.VALID.getTypeString());
                            break;
                        }
                        else if(randomValue%3 == 2 && 0 < testSize) {
                            testSize -= 1;
                            BsonUtils.addToMap(metaData.getBody(), "type", MLType.TEST.getTypeString());
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.TEST.getTypeString());
                            break;
                        } else {
                            mod -= 1;
                        }
                    }

                    case 2 : {
                        if (trainSize == 0) {
                            if (randomValue%2 == 0 && 0 < validSize ){
                                validSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.VALID.getTypeString());
                                break;
                            }
                            else if (randomValue%2 == 1 && 0 < testSize ){
                                testSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.TEST.getTypeString());
                                break;
                            } else {
                                mod -= 1;
                            }
                        }
                        else if (validSize == 0){
                            if (randomValue%2 == 0 && 0 < trainSize ){
                                trainSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.TRAIN.getTypeString());
                                break;
                            }
                            else if (randomValue%2 == 1 && 0 < testSize ){
                                testSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.TEST.getTypeString());
                                break;
                            } else {
                                mod -= 1;
                            }
                        }
                        else {
                            if (randomValue%2 == 0 && 0 < trainSize ){
                                trainSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.TRAIN.getTypeString());
                                break;
                            }
                            else if (randomValue%2 == 1 && 0 < validSize ){
                                validSize -= 1;
                                metaDataRepository.updateType(metaData.getMetadataId(),MLType.VALID.getTypeString());
                                break;
                            } else {
                                mod -= 1;
                            }

                        }

                    }

                    case 1 : {
                        if(trainSize != 0){
                            trainSize -= 1;
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.TRAIN.getTypeString());
                            break;
                        }
                        else  if (validSize != 0){
                            validSize -= 1;
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.VALID.getTypeString());
                            break;
                        } else {
                            testSize -= 1;
                            metaDataRepository.updateType(metaData.getMetadataId(),MLType.TEST.getTypeString());
                            break;
                        }

                    }

                }

            }

            sec = System.currentTimeMillis() - sec ; // 코드 실행 후에 시간 받아오기

            log.info("sampling - thread "+seq+ "실행 시간(m) : "+ sec);
        }
    }


}
