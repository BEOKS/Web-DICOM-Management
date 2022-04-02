package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.databind.JsonNode;

public interface MalignancyServerMessenger extends TorchServerMessenger{
    /**
     * @param instanceID 추론에 사용할 Dicom 이미지의 ID, orthanc의 ID가 아닌 메타데이터에 저장된 ID를 의미합니다.
     * @return 머신러닝 처리 결과 중 분류기 모델의 데이터를 반환합니다.
     */
    public JsonNode getClassificationData(String instanceID);

    /**
     * @param instanceID 추론에 사용할 Dicom 이미지의 ID, orthanc의 ID가 아닌 메타데이터에 저장된 ID를 의미합니다.
     * @return 머신러닝 처리 결과 중 Segmentation 모델의 데이터를 반환합니다.
     */
    public JsonNode getSegmentationData(String instanceID);
}
