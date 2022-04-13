package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.databind.JsonNode;
import com.knuipalab.dsmp.service.storage.FileSystemStorageService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Primary
@Service
public class BasicMalignancyServerMessenger implements MalignancyServerMessenger{
    private final String URL="http://host.docker.internal:8098/predictions/Malignancy";
    String charset = "UTF-8";
    private FileSystemStorageService fileSystemStorageService;

    public BasicMalignancyServerMessenger(FileSystemStorageService fileSystemStorageService){
        this.fileSystemStorageService=fileSystemStorageService;
    }

    private HttpResponse postImageToServer(String url,File file) {
        HttpEntity entity= MultipartEntityBuilder.create()
                .addPart("data",new FileBody(file))
                .build();
        HttpPost request=new HttpPost(url);
        request.setEntity(entity);
        HttpClient client = HttpClientBuilder.create().build();
        try {
            return client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonNode requestMalignancyInference(String projectId, String imageName) {
        File imageFile=this.fileSystemStorageService.serveFile(projectId,imageName);
        HttpResponse httpResponse=postImageToServer(URL,imageFile);
        try {
            String json = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("asdf"+json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
