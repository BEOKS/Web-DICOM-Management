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
    public ResponseEntity<? extends BasicResponse> typeSampling(@PathVariable String projectId) {
        asyncMetaDataSampler.typeSampling(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    /**
     * 프로젝트 아이디를 통해서 요청하면,
     * 해당 프로젝트에 포함된 이미지 데이터들에 대한 머신러닝 추론이 시작됩니다.
     * 수치형 데이터결과는 메타데이터에 추가되며, 이미지 데이터 결과는 .cam 확장자가
     * 중간에 삽입되어서 이미지 데이터에 저장됩니다.(ex. 123.jpg -> 123.cam.png)
     * @param projectId
     * @return
     */
    @PutMapping("api/MetaData/MalignancyClassification/{projectId}")
    public ResponseEntity<? extends BasicResponse> requestMLInference(@PathVariable String projectId) {
        asyncMetaDataSampler.requestMLInferenceToTorchServe(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

}
