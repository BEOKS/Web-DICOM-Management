package com.knuipalab.dsmp.controller.metadata.api;


import com.knuipalab.dsmp.dto.metadata.MetaDataCreateRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataUpdateRequestDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MetaDataApiController {

    @Autowired
    private MetaDataService metaDataService;

    // projectId가 같은 metadata를 모두 반환
    @GetMapping("api/MetaData/{projectId}")
    public List<MetaDataResponseDto> findByProjectId(@PathVariable String projectId){
        return metaDataService.findByProjectId(projectId);
    }

    // metatdata를 projectId와 함께 저장
    @PostMapping("api/MetaData/{projectId}")
    public void insert(@PathVariable String projectId,@RequestBody String strBody){
        MetaDataCreateRequestDto metaDataCreateRequestDto = new MetaDataCreateRequestDto(projectId,strBody);
        metaDataService.insert(metaDataCreateRequestDto);
    }

    // metatdata의 body부분을 수정
    @PutMapping("api/MetaData/{metadataId}")
    public void update(@PathVariable String metadataId,@RequestBody String strBody){
        MetaDataUpdateRequestDto metaDataUpdateRequestDto = new MetaDataUpdateRequestDto(strBody);
        metaDataService.update(metadataId,metaDataUpdateRequestDto);
    }

    // metadataId를 기반으로 삭제
    @DeleteMapping("api/MetaData/{metadataId}")
    public void deleteById(@PathVariable String metadataId){
        metaDataService.deleteById(metadataId);
    }

}
