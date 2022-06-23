package com.knuipalab.dsmp.machineLearning.MachineLearningInference;

import com.fasterxml.jackson.databind.JsonNode;
import com.knuipalab.dsmp.metadata.MetaData;
import com.knuipalab.dsmp.metadata.MetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AsyncMLInferenceService implements MLInferenceService{

    private final MalignancyServerMessenger malignancyServerMessenger;

    private final MetaDataRepository metaDataRepository;

    @Override
    public void requestMLInferenceToTorchServe(final String projectId, final String modelName) {
        metaDataRepository.findByProjectId(projectId)
                .parallelStream()
                .map(metaData -> getClassificationInfo(metaData,projectId,modelName))
                .forEach(ClassificationInfo::saveToDatabase);
    }

    private ClassificationInfo getClassificationInfo(MetaData metaData,String projectId,String modelName) {
         return new ClassificationInfo(malignancyServerMessenger.requestMalignancyInference(
                 projectId,metaData.getImageNameFromBody(),modelName), metaData.getMetadataId());
    }
    class ClassificationInfo{
        private JsonNode jsonNode;
        private String metadataId;
        public ClassificationInfo(JsonNode jsonNode,String metadataId){
            this.jsonNode=jsonNode;
            this.metadataId=metadataId;
        }
        protected void saveToDatabase(){
            HashMap<String,Object> classificationSet = new HashMap<>();
            jsonNode.fields().forEachRemaining(
                    field -> { classificationSet.put(field.getKey(),field.getValue()); }
            );
            metaDataRepository.setMalignancyClassification(metadataId,classificationSet);
        }
    }
}
