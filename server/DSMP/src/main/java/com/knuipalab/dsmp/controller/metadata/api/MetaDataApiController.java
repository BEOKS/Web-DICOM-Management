package com.knuipalab.dsmp.controller.metadata.api;

import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController //json 반환
public class MetaDataApiController {

    @Autowired
    private MetaDataService metaDataService;

    @GetMapping("/MetaData")
    public List<MetaDataResponseDto> findAll_MetaData(){
        System.out.println(metaDataService.findAll_MetaData());
        return metaDataService.findAll_MetaData();//Jackson Library를 통해 자바 객체를 json형식으로 반환
    }

}
