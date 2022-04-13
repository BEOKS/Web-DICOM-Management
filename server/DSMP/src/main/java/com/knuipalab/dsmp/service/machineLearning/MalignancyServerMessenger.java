package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Pytorch Server에서 Malignancy
 */
public interface MalignancyServerMessenger extends TorchServerMessenger{
    /**
     * 프로젝트 아이디를 통해서 요청하면,
     * 해당 프로젝트에 포함된 이미지 데이터들에 대한 머신러닝 추론이 시작됩니다.
     * 수치형 데이터결과는 메타데이터에 추가되며, 이미지 데이터 결과는 .cam 확장자가
     * 중간에 삽입되어서 이미지 데이터에 저장됩니다.(ex. 123.jpg -> 123.cam.png)
     * @param projectId
     * @return 수치형 데이터가 JsonNode 형태로 반환됩니다.
     */
    public JsonNode requestMalignancyInference(String projectId,String imageName);
}
