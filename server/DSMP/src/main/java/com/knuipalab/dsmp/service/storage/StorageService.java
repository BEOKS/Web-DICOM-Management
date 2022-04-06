package com.knuipalab.dsmp.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void uploadFile(String projectId, MultipartFile file);
    void deleteAll(String projectId);
}
