package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.databind.JsonNode;
import com.knuipalab.dsmp.service.storage.FileSystemStorageService;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Primary
@Service
public class BasicMalignancyServerMessenger implements MalignancyServerMessenger{
    private final String URL="http://host.docker.internal:8098/predictions/Malignancy";
    String charset = "UTF-8";
    private FileSystemStorageService fileSystemStorageService;

    public BasicMalignancyServerMessenger(FileSystemStorageService fileSystemStorageService){
        this.fileSystemStorageService=fileSystemStorageService;
    }


    @Override
    public JsonNode requestMalignancyInference(String projectId, String imageName) {
        File imageFile=this.fileSystemStorageService.serveFile(projectId,imageName);
        return null;
    }

    @Override
    public boolean isServerAvailable() {
        return false;
    }

    public HttpResponse sendRequest(File file){

        return null;
    }
}
