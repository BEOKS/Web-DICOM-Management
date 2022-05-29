package com.knuipalab.dsmp.metadata;
import com.google.common.collect.Lists;

import com.knuipalab.dsmp.http.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.http.httpResponse.error.handler.exception.MetaDataNotFoundException;
import com.knuipalab.dsmp.patient.PatientService;
import com.knuipalab.dsmp.project.ProjectService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    private void throwGlobalExceptionWhenProjectNotExist(String projectId){
        projectService.findById(projectId);
    }
    @Transactional (readOnly = true)
    public List<MetaDataResponseDto> findByProjectId(String projectId) {
        throwGlobalExceptionWhenProjectNotExist(projectId);
        return metaDataRepository.findByProjectId(projectId).stream()
                .map( metaData -> new MetaDataResponseDto(metaData) )
                .collect(Collectors.toList());
    }
    @Getter
    public class PageRequestConfiguration{
        int pageNumber;
        int size;
        PageRequestConfiguration(HashMap parmMap,int defaultPageNumber,int defaultSize){
            if(parmMap.containsKey("page")){
                this.pageNumber = Integer.parseInt(parmMap.get("page").toString());
            }
            else{
                this.pageNumber=defaultPageNumber;
            }
            if(parmMap.containsKey("size")){
                this.size = Integer.parseInt(parmMap.get("size").toString());
            }
            else{
                this.size=defaultSize;
            }
        }
    }
    @Transactional (readOnly = true)
    public Page<MetaData> findByProjectIdWithPaging(String projectId, HashMap httpRequestParameterMap) {

        PageRequestConfiguration pageRequestConfiguration=new PageRequestConfiguration(httpRequestParameterMap,0,20);
        if(httpRequestParameterMap.containsKey("page")){
            httpRequestParameterMap.remove("page");
        }
        if(httpRequestParameterMap.containsKey("size")){
            httpRequestParameterMap.remove("size");
        }
        throwGlobalExceptionWhenProjectNotExist(projectId);
        return metaDataRepository.findByProjectIdWithPaging(projectId,
                pageRequestConfiguration.getPageNumber(), pageRequestConfiguration.getSize(), httpRequestParameterMap);

    }

    @Transactional
    public void save(MetaDataCreateRequestDto metaDataCreateRequestDto){

        throwGlobalExceptionWhenProjectNotExist(metaDataCreateRequestDto.getProjectId());

        MetaData metaData = new MetaData().builder()
                .projectId(metaDataCreateRequestDto.getProjectId())
                .body(metaDataCreateRequestDto.getBody()).build();

        patientService.addProjectCount(metaData.getPatientIdFromBody());

        metaDataRepository.save(metaData);
    }

    @Transactional
    public void insertAllByMetaDataList(MetaDataCreateAllRequestDto metaDataCreateAllRequestDto){

        String projectId = metaDataCreateAllRequestDto.getProjectId();
        throwGlobalExceptionWhenProjectNotExist(projectId);

        if(metaDataCreateAllRequestDto.getBodyList() != null){
            List<MetaData> metaDataList = getMetaDataFromMetaDataCreateAllRequestDto(metaDataCreateAllRequestDto, projectId);
            metaDataList.forEach(metaData -> patientService.addProjectCount(metaData.getPatientIdFromBody()));
            int chunk_size = 1000;
            Lists.partition(metaDataList,chunk_size).forEach(batch -> metaDataRepository.saveAll(batch) );
        }
    }

    @NotNull
    private List<MetaData> getMetaDataFromMetaDataCreateAllRequestDto(MetaDataCreateAllRequestDto metaDataCreateAllRequestDto, String projectId) {
        return metaDataCreateAllRequestDto.getBodyList().stream()
                .map(body -> new MetaData().builder().projectId(projectId).body(body).build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllByMetaDataIdList(MetaDataDeleteAllRequestDto metaDataDeleteAllRequestDto){

        throwGlobalExceptionWhenProjectNotExist((metaDataDeleteAllRequestDto.getProjectId()));

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
