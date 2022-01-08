package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateRequestDto;

import com.knuipalab.dsmp.dto.metadata.MetaDataUpdateRequestDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service //IoC 대상이다.
public class MetaDataService {

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Autowired
    private ProjectService projectService;

//    @Transactional (readOnly = true)
//    public List<MetaDataResponseDto> findAll(){
//
//        List <MetaDataResponseDto> metaResponseDtoList = new ArrayList<MetaDataResponseDto>();
//        List <MetaData> metaDataList = metaDataRepository.findAll();
//
//        for(MetaData metaData: metaDataList){ // metaDataRepository로 MeataData 정보 받아와서 Dto로 전환 -> 접근성 제한 목적
//            metaResponseDtoList.add(new MetaDataResponseDto(metaData));
//        }
//
//        return metaResponseDtoList;
//    }

    @Transactional (readOnly = true)
    public List<MetaDataResponseDto> findByProjectId(String projectId) {

        projectService.findById(projectId);

        List <MetaDataResponseDto> metaResponseDtoList = new ArrayList<MetaDataResponseDto>();
        List <MetaData> metaDataList = metaDataRepository.findByProjectId(projectId);

        for(MetaData metaData: metaDataList){
            metaResponseDtoList.add(new MetaDataResponseDto(metaData));
        }

        return metaResponseDtoList;
    }

    @Transactional
    public void insert(MetaDataCreateRequestDto metaDataCreateRequestDto){


        projectService.findById(metaDataCreateRequestDto.getProjectId());

        MetaData metaData = new MetaData().builder()
                .projectId(metaDataCreateRequestDto.getProjectId())
                .body(metaDataCreateRequestDto.getBody()).build();

        metaDataRepository.save(metaData);
    }

    @Transactional
    public void update(String metadataId, MetaDataUpdateRequestDto metaDataUpdateRequestDto){

        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new IllegalArgumentException("해당 metadataId 값을 가진 메타데이터 정보가 없습니다."));

        metaData.update(metaDataUpdateRequestDto.getBody());

        metaDataRepository.save(metaData);
    }

    @Transactional
    public void delete(String metadataId){
        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new IllegalArgumentException("해당 metadataId 값을 가진 메타데이터 정보가 없습니다."));

        metaDataRepository.delete(metaData);
    }

}
