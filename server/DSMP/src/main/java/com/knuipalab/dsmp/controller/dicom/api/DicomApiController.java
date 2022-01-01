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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class DicomApiController {

    //    {
//        ID: "",
//        ParentPatient : "",
//        ParentSeries : "",
//        ParentStudy : "",
//        Path : "",
//        Status : ""
//    }

//    @PostMapping("/api/dicom")
//    public String uploadDicom(@RequestParam("dicomfile") MultipartFile file,
//                                RedirectAttributes redirectAttributes) throws IOException {
//        System.out.println(file.getSize());
//        System.out.println(file.getContentType());
//        redirectAttributes.addFlashAttribute(file);
//        return "redirect:http://localhost:8042/instances";
//    }
//    @Autowired
//    MultipartFileServeice multipartFileServeice;

    @PostMapping("/api/dicom")
    public String uploadDicom(@RequestPart("dicomfile") MultipartFile file) throws IOException {
        System.out.println(file.getSize());
        System.out.println(file.getInputStream());
        System.out.println(file.toString());
        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

//        File newfile = new File(file.getOriginalFilename());
//        file.transferTo(newfile);

        String BASE_URL = "http://localhost:8042/instances";

        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());

        RestTemplate restTemplate = new RestTemplate(factory);
//        RestTemplate restTemplate = new RestTemplate();

//        header setting
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/dicom");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

//        body setting
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("application/dicom", file.getBytes());

//        post api call
        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Object> response = restTemplate.postForEntity(BASE_URL, requestEntity, Object.class);

        System.out.println(response.toString());

//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(response.getBody());

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