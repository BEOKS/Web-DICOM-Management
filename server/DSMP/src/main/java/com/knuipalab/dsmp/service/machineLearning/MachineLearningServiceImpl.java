package com.knuipalab.dsmp.service.machineLearning;

import com.google.common.collect.Lists;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.util.BsonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class MachineLearningServiceImpl implements MachineLearningService{

    private final MetaDataRepository metaDataRepository;

    @Override
    public void typeSampling(String projectId) {

        List<MetaData> metaDataList = metaDataRepository.findByProjectId(projectId);

        final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_THREADS);

        int seq = 0;
        int chunk_size = 1000;
        List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
        for (List<MetaData> batch : Lists.partition(metaDataList,chunk_size)) {
            Future f = executor.submit(new updateThread(batch, seq));
            futures.add(f);
            seq += 1;
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

            double trainPercent = 0.7 , validPercent = 0.2 , testPercent = 0.1 ;
            int metaDataListSize = metaDataList.size();
            int trainSize = (int)(metaDataListSize * trainPercent);
            int validSize = (int)(metaDataListSize * validPercent);
            int testSize = metaDataListSize - (trainSize+validSize);
            int randomValue;
            int mod = 3;

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
        }
    }

}
