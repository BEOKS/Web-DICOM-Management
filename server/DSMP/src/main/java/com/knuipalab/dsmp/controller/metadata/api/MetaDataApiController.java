package com.knuipalab.dsmp.controller.metadata.api;

import com.knuipalab.dsmp.dto.metadata.*;
import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.metadata.MetaDataService;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class MetaDataApiController {

    @Autowired
    private MetaDataService metaDataService;

    // projectId가 같은 metadata를 모두 반환
    @GetMapping("api/MetaData/{projectId}")
    public ResponseEntity< ? extends BasicResponse> findByProjectId(@PathVariable String projectId){
        List<MetaDataResponseDto> metaDataResponseDtos = metaDataService.findByProjectId(projectId);
        return ResponseEntity.ok().body(new SuccessDataResponse<List<MetaDataResponseDto>>(metaDataResponseDtos));
    }

    // metatdata를 projectId와 함께 저장
    @PostMapping("api/MetaData/{projectId}")
    public ResponseEntity<? extends  BasicResponse> insert(@PathVariable String projectId,@RequestBody Document body){
        MetaDataCreateRequestDto metaDataCreateRequestDto = new MetaDataCreateRequestDto(projectId,body);
        metaDataService.insert(metaDataCreateRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // 다수개의 metadata를 projectId와 함께 저장
    @PostMapping("api/MetaDataList/insert/{projectId}")
    public ResponseEntity<? extends  BasicResponse> insertAllByMetaDataList(@PathVariable String projectId,@RequestBody List<Document> bodyList){
        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(projectId,bodyList);
        metaDataService.insertAllByMetaDataList(metaDataCreateAllRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    @PostMapping("api/MetaDataList/delete/{projectId}")
    public ResponseEntity<? extends  BasicResponse> deleteAllByMetaDataIdList(@PathVariable String projectId,@RequestBody List<String> metaDataIdList){
        MetaDataDeleteAllRequestDto metaDataDeleteAllRequestDto = new MetaDataDeleteAllRequestDto(projectId,metaDataIdList);
        metaDataService.deleteAllByMetaDataIdList(metaDataDeleteAllRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // metatdata의 body부분을 수정
    @PutMapping("api/MetaData/{metadataId}")
    public ResponseEntity<? extends  BasicResponse> update(@PathVariable String metadataId,@RequestBody Document body){
        MetaDataUpdateRequestDto metaDataUpdateRequestDto = new MetaDataUpdateRequestDto(metadataId,body);
        metaDataService.update(metaDataUpdateRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // metadataId를 기반으로 삭제
    @DeleteMapping("api/MetaData/{metadataId}")
    public ResponseEntity<? extends  BasicResponse>  deleteById(@PathVariable String metadataId){
        metaDataService.deleteById(metadataId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

}
