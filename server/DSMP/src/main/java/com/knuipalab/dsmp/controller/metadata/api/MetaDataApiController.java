package com.knuipalab.dsmp.controller.metadata.api;

import com.knuipalab.dsmp.dto.metadata.US_MetaDataResponseDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController //json 반환
public class MetaDataApiController {

    @Autowired
    private MetaDataService metaDataService;

    @GetMapping("/MeataData/US")
    public List<US_MetaDataResponseDto> findAll_US_MetatData(){
        return metaDataService.findAll_US_MetatData();//Jackson Library를 통해 자바 객체를 json형식으로 반환
    }

}
