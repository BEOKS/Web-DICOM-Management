package com.knuipalab.dsmp.controller.dicom.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class DicomApiController {

    @PostMapping("/api/dicom")
    public JsonNode uploadDicom(@RequestPart("dicomfile") MultipartFile file) throws IOException {

        System.out.println("--------/api/dicom POST api Call------------");
        System.out.println("-----------Dicom File Info------------");
        System.out.println("File Name: " + file.getOriginalFilename());
        System.out.println("Content-type: " + file.getContentType());
        System.out.println("File Size: " + file.getSize());
        System.out.println("File: " + file.toString());

        String BASE_URL = "http://localhost:8042/instances";
//        String BASE_URL = "https://demo.orthanc-server.com/instances";

        RestTemplate restTemplate = new RestTemplate();

//        header setting
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/dicom");

//        post api call
        byte[] content = file.getBytes();
        HttpEntity<byte []> requestEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, requestEntity, String.class);

//        reponse to json
        System.out.println("-----------Response Result------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            jsonNode = objectMapper.readTree(response.toString());
        }
        System.out.println(response.toString());

        return jsonNode;
    }

    @GetMapping("/api/dicom")
    public String listDicom(){
        String BASE_URL = "http://localhost:8042/instances";
        RestTemplate restTemplate = new RestTemplate();

//        get api call
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        System.out.println(response.toString());

        return response.toString();
    }

    @PostMapping("/api/dicom3")
    public JSONObject UserLogoutInterface(@RequestPart("dicomfile") MultipartFile file) throws IOException {
        String BASE_URL = "http://localhost:8042/instances";

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        File newfile = new File(file.getOriginalFilename());
        file.transferTo(newfile);

        System.out.println(newfile.getName());
        System.out.println(newfile.getPath());

        // 전달하고자 하는 데이터를 JSON 형식으로 FormDataPart에 삽입.
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(okhttp3.MediaType.parse("application/dicom"), newfile))
                .build();

        // url안의 주소는 호출하고자 하는 API의 주소 및 포트번호
        Request request = new Request.Builder()
                .header("Content-Type", "application/dicom")
                .url(BASE_URL)
                .post(body)
                .build();

        System.out.println(request.toString());
//        System.out.println(request.body());

        Response response = client.newCall(request).execute();
//        System.out.println(response.toString());
        System.out.println(response.headers().toString());
//        System.out.println(response.body().toString());
//        if (response.isSuccessful()) {
//            ResponseBody rbody = response.body();
//
//            if (rbody != null)
//                System.out.println("rbody" + rbody.toString());
//                System.out.println("jsonText" + jsonText.toString());
//                rbody.close();
//
//                JSONObject json = new JSONObject(jsonText);
//            }
//        }
//        System.out.println("aaa" + response.body().string().toString());

//        JSONObject json = new JSONObject(response.body().string());
        JSONObject json = new JSONObject("{}");
        return json;
    }
}