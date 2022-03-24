package com.knuipalab.dsmp.service.machineLearning;

public interface MachineLearningService {
    // projectId에 대한 metadata type을 랜덤적으로 샘플링 한다.
    void typeSampling(String projectId);
}
