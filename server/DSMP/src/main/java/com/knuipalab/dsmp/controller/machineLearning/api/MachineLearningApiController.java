package com.knuipalab.dsmp.controller.machineLearning.api;

import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.machineLearning.AsyncMetaDataSampler;
import com.knuipalab.dsmp.service.machineLearning.BasicMalignancyServerMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MachineLearningApiController {

    private final AsyncMetaDataSampler asyncMetaDataSampler;
    private final BasicMalignancyServerMessenger basicMalignancyServerMessenger;
    /**
     * 머신러닝 추론을 테스트하기 위한 임시 API 입니다.
     * @return
     */
    @GetMapping("api/ML/sampleRequest")
    public ResponseEntity<? extends BasicResponse> sampleRequest(){
        return ResponseEntity.ok().body(new SuccessDataResponse<String>(basicMalignancyServerMessenger.requestMalignancyInference("62552fea40ddf80e5b77eb49",
                "c_116627_m_l_1_1.jpg").toString()));
    }

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
