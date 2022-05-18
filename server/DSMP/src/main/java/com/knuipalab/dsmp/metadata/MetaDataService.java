package com.knuipalab.dsmp.metadata;
import com.google.common.collect.Lists;

import com.knuipalab.dsmp.http.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.http.httpResponse.error.handler.exception.MetaDataNotFoundException;
import com.knuipalab.dsmp.patient.PatientService;
import com.knuipalab.dsmp.project.ProjectService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    @Transactional (readOnly = true)
    public Page<MetaData> findByProjectIdWithPaging(String projectId, HashMap parmMap) {

        //default page and size
        int page = 0;
        int size = 20;
        if(parmMap.containsKey("page")){
            page = Integer.parseInt(parmMap.get("page").toString());
            parmMap.remove("page");
        }
        if(parmMap.containsKey("size")){
            size = Integer.parseInt(parmMap.get("size").toString());
            parmMap.remove("size");
        }

        projectService.findById(projectId);

        return metaDataRepository.findByProjectIdWithPaging(projectId, page, size, parmMap);

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
    public void insertAllByMetaDataList(MetaDataCreateAllRequestDto metaDataCreateAllRequestDto){

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

            int chunk_size = 1000;
            for (List<MetaData> batch : Lists.partition(metaDataList,chunk_size)) {
                metaDataRepository.saveAll(batch);
            }

        }
    }

    @Transactional
    public void deleteAllByMetaDataIdList(MetaDataDeleteAllRequestDto metaDataDeleteAllRequestDto){

        projectService.findById(metaDataDeleteAllRequestDto.getProjectId()); // 존재하는 프로젝트 id인지 확인.

        List<MetaData> metaDataList = metaDataDeleteAllRequestDto.getMetadataIdList().stream()
                .map( metadataId ->  metaDataRepository.findById(metadataId).orElseThrow(()-> new MetaDataNotFoundException(ErrorCode.METADATA_NOT_FOUND)))
                .collect(Collectors.toList());

        metaDataRepository.deleteAll(metaDataList);
    }

    @Transactional
    public void update(MetaDataUpdateRequestDto metaDataUpdateRequestDto){

        MetaData metaData = metaDataRepository.findById(metaDataUpdateRequestDto.getMetadataId())
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
