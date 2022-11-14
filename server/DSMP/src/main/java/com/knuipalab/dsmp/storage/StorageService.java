package com.knuipalab.dsmp.storage;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StorageService {
    void uploadFile(String projectId, MultipartFile file);
    void deleteAll(String projectId);
    void deleteByFileName(String projectId, String fileName);
    List<String> getFileList(String projectId);
    void serveFile(String projectId, String fileName , HttpServletRequest request, HttpServletResponse response);
}
