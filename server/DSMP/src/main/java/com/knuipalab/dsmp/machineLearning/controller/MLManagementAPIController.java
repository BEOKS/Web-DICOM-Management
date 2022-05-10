package com.knuipalab.dsmp.machineLearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface MLManagementAPIController {
    /**
     * 모델 리스트를 Json 형태의 String 값으로 반환합니다.
     * example
     * {
     *   "models": [
     *     {
     *       "modelName": "Malignancy",
     *       "modelUrl": "Malignancy.mar"
     *     }
     *   ]
     * }
     * @return
     */
    @GetMapping("api/ML/getModelList")
    public ResponseEntity<String> getModelList();
}
