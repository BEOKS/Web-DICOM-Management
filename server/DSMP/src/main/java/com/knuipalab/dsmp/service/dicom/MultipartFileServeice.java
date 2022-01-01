//package com.knuipalab.dsmp.service.dicom;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//@Service
//public class MultipartFileServeice {
//
////    @Bean
////    public MultipartFile getMultipartBean(MultipartFile file) {
////
////        byte[] content = null;
////        try {
////            content = file.getInputStream().readAllBytes();
////        }
////        catch(Exception ex) {
////            System.out.println("Error: multipart input stream error");
////        }
////
////        MultipartFile beanMultipart = new MockMultipartFile(
////                file.getName(),
////                file.getOriginalFilename(),
////                file.getContentType(),
////                content);
////
////        return beanMultipart;
////    }
//}
