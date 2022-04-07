package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Pytorch Server에서 Malignancy
 */
public interface MalignancyServerMessenger extends TorchServerMessenger{
    /**
     * @param instanceID 추론에 사용할 Dicom 이미지의 ID, orthanc의 ID가 아닌 메타데이터에 저장된 SOPInstanceID를 의미합니다.
     *                   메타데이터의 image_name 속성에서 해당 정보를 가져올 수 있습니다.
     * @return 머신러닝 처리 결과 중 분류기 모델의 데이터를 반환합니다.
     */
    public JsonNode getClassificationData(String instanceID);

    /**
     * @param instanceID 추론에 사용할 Dicom 이미지의 ID, orthanc의 ID가 아닌 메타데이터에 저장된 ID를 의미합니다.
     * @return 머신러닝 처리 결과 중 Segmentation 모델의 데이터를 반환합니다.
     */
    public JsonNode getSegmentationData(String instanceID);
}
