package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateAllRequestDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataCreateRequestDto;

import com.knuipalab.dsmp.dto.metadata.MetaDataUpdateRequestDto;
import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.MetaDataNotFoundException;
import com.knuipalab.dsmp.service.patient.PatientService;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        return metaDataRepository.findByProjectId(projectId).stream()
                .map( metaData -> new MetaDataResponseDto(metaData) )
                .collect(Collectors.toList());
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

        List<MetaData> metaDataList=new ArrayList<MetaData>();

        projectService.findById(metaDataCreateAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        if(metaDataCreateAllRequestDto.getBodyList() != null){

            List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();

            String projectId = metaDataCreateAllRequestDto.getProjectId();

            for(Document body : bodyList){
                MetaData metaData = new MetaData().builder()
                        .projectId(projectId)
                        .body(body).build();
                metaDataList.add(metaData);
                patientService.addProjectCount(metaData.getPatientIdFromBody());
            }

            metaDataRepository.insert(metaDataList);

        }
    }

    @Transactional
    public void update(String metadataId, MetaDataUpdateRequestDto metaDataUpdateRequestDto){

        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new MetaDataNotFoundException(ErrorCode.METADATA_NOT_FOUND));

        metaData.update(metaDataUpdateRequestDto.getBody());

        metaDataRepository.save(metaData);
    }

    @Transactional
    public void deleteById(String metadataId){

        MetaData metaData = metaDataRepository.findById(metadataId)
                .orElseThrow(()-> new MetaDataNotFoundException(ErrorCode.METADATA_NOT_FOUND));

        patientService.minusProjectCount(metaData.getPatientIdFromBody()); // patient 처리.

        metaDataRepository.delete(metaData);
    }
    @Transactional
    public Long deleteAllByProjectId(String projectId){

        projectService.findById(projectId); // 존재하는 프로젝트 id인지 확인.

        return metaDataRepository.deleteAllByProjectId(projectId);
    }

}
