package com.knuipalab.dsmp.machineLearning.service;

public interface MetaDataSampler {
    // projectId에 대한 metadata type을 랜덤적으로 샘플링 한다.
    void typeSampling(String projectId);
    void requestMLInferenceToTorchServe(String projectId,String modelName);
}
