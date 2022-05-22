package com.knuipalab.dsmp.machineLearning;

import com.knuipalab.dsmp.http.httpResponse.BasicResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.machineLearning.MachineLearningInference.AsyncMLInferenceService;
import com.knuipalab.dsmp.machineLearning.TrainTypeSampling.AsyncMetaDataSampler;
import com.knuipalab.dsmp.machineLearning.MachineLearningInference.BasicMalignancyServerMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MachineLearningApiController implements MLManagementAPIController{

    private final AsyncMetaDataSampler asyncMetaDataSampler;
    private final BasicMalignancyServerMessenger basicMalignancyServerMessenger;
    private final AsyncMLInferenceService asyncMLInferenceService;

    /**
     * 머신러닝 추론을 테스트하기 위한 임시 API 입니다.
     * 
     * @return
     */
    @GetMapping("api/ML/sampleRequest/{projectId}/{imageName}")
    public ResponseEntity<? extends BasicResponse> sampleRequest(@PathVariable String projectId,
            @PathVariable String imageName) {
        return ResponseEntity.ok().body(new SuccessDataResponse<String>(
                basicMalignancyServerMessenger.requestMalignancyInference(projectId, imageName,"").toString()));
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
     * 
     * @param projectId
     * @return
     */
    @PutMapping("api/MetaData/MalignancyClassification/{projectId}/{modelName}")
    public ResponseEntity<? extends BasicResponse> requestMLInference(@PathVariable String projectId, @PathVariable String modelName) {
        asyncMLInferenceService.requestMLInferenceToTorchServe(projectId,modelName);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    @Override
    @GetMapping("api/ML/getModelList")
    public ResponseEntity<String> getModelList() {
        return basicMalignancyServerMessenger.getRunningModelList();
    }
}
