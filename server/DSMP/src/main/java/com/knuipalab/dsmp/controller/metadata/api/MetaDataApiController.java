package com.knuipalab.dsmp.controller.metadata.api;


import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataRequestDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //json 반환
public class MetaDataApiController {

    @Autowired
    private MetaDataService metaDataService;

    @GetMapping("/MetaData/{id}")
    public MetaDataResponseDto findById(@PathVariable String id){
        return metaDataService.findById(id);
    }

    @PutMapping("/MetaData/{id}")
    public void update(@PathVariable String id,@RequestBody String strBody){
        MetaDataRequestDto metaDataRequestDto = new MetaDataRequestDto(strBody);
        metaDataService.update(id,metaDataRequestDto);
    }

    @DeleteMapping("/MetaData/{id}")
    public void deleteById(@PathVariable String id){
        metaDataService.deleteById(id);
    }

    @GetMapping("/MetaData")
    public List<MetaDataResponseDto> findAll(){
        return metaDataService.findAll();//Jackson Library를 통해 자바 객체를 json형식으로 반환
    }

    @PostMapping("/MetaData")
    public void insert(@RequestBody String strBody){
        MetaDataRequestDto metaDataRequestDto = new MetaDataRequestDto(strBody);
        metaDataService.insert(metaDataRequestDto);
    }

}
