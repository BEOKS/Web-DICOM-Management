package com.knuipalab.dsmp.controller.machineLearning.api;

import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.machineLearning.AsyncMetaDataSampler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MachineLearningApiController {

    private final AsyncMetaDataSampler asyncMetaDataSampler;

    @PutMapping("api/MetaData/Sampling/{projectId}")
    public ResponseEntity< ? extends BasicResponse> typeSampling(@PathVariable String projectId){
        asyncMetaDataSampler.typeSampling(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    @PutMapping("api/MetaData/MalignancyClassification/{projectId}")
    public ResponseEntity< ? extends BasicResponse> setMalignancyClassification(@PathVariable String projectId){
        asyncMetaDataSampler.setMalignancyClassification(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

}
