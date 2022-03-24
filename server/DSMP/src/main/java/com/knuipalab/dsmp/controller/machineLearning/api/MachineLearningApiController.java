package com.knuipalab.dsmp.controller.machineLearning.api;

import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.machineLearning.MachineLearningServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MachineLearningApiController {

    private final MachineLearningServiceImpl machineLearningService;

    @PutMapping("api/MetaData/Sampling/{projectId}")
    public ResponseEntity< ? extends BasicResponse> typeSampling(@PathVariable String projectId){
        machineLearningService.typeSampling(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }
}
