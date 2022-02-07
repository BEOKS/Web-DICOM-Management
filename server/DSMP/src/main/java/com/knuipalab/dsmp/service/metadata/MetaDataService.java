package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateAllRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateRequestDto;

import com.knuipalab.dsmp.dto.metadata.MetaDataUpdateRequestDto;
import com.knuipalab.dsmp.service.patient.PatientService;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.bson.Document;
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

    @Autowired
    private PatientService patientService;

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

        projectService.findById(metaDataCreateRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        MetaData metaData = new MetaData().builder()
                .projectId(metaDataCreateRequestDto.getProjectId())
                .body(metaDataCreateRequestDto.getBody()).build();
        patientService.addProjectCount(metaData.getPatientIdFromBody()); // patient 처리.
        metaDataRepository.save(metaData); // 저장
    }

    @Transactional
    public void insertAll(MetaDataCreateAllRequestDto metaDataCreateAllRequestDto){
        projectService.findById(metaDataCreateAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.
        if(metaDataCreateAllRequestDto.getBodyList() != null){
            List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();
            String projectId = metaDataCreateAllRequestDto.getProjectId();
            for(Document body : bodyList){
                MetaData metaData = new MetaData().builder()
                        .projectId(projectId)
                        .body(body).build();
                patientService.addProjectCount(metaData.getPatientIdFromBody());
                metaDataRepository.save(metaData);
            }
        }
    }

    @Transactional
    public void update(String metadataId, MetaDataUpdateRequestDto metaDataUpdateRequestDto){

        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new IllegalArgumentException("해당 metadataId 값을 가진 메타데이터 정보가 없습니다."));

        metaData.update(metaDataUpdateRequestDto.getBody());

        metaDataRepository.save(metaData);
    }

    @Transactional
    public void deleteById(String metadataId){

        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new IllegalArgumentException("해당 metadataId 값을 가진 메타데이터 정보가 없습니다."));

        patientService.minusProjectCount(metaData.getPatientIdFromBody()); // patient 처리.

        metaDataRepository.delete(metaData);
    }
    @Transactional
    public Long deleteAllByProjectId(String projectId){
        return metaDataRepository.deleteAllByProjectId(projectId);
    }

}
