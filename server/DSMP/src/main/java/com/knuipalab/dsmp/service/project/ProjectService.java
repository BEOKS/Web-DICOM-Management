package com.knuipalab.dsmp.service.project;

import com.knuipalab.dsmp.configuration.auth.dto.SessionUser;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.project.ProjectRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.metadata.MetaDataService;
import com.knuipalab.dsmp.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MetaDataService metaDataService;

    @Transactional (readOnly = true)
    public List<ProjectResponseDto> findByUserId(){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");

        List <ProjectResponseDto> projectResponseDtoList = new ArrayList<ProjectResponseDto>();
        List <Project> projectList = projectRepository.findByUserId(sessionUser.getUserId());

        for(Project project: projectList){
            projectResponseDtoList.add(new ProjectResponseDto(project));
        }

        return projectResponseDtoList;
    }

    @Transactional (readOnly = true)
    public ProjectResponseDto findById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        return new ProjectResponseDto(project);
    }

    @Transactional
    public void insert(ProjectRequestDto projectRequestDto){

        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
        Project project = new Project().builder()
                .projectName(projectRequestDto.getProjectName())
                .userId(sessionUser.getUserId())
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void update(String projectId,ProjectRequestDto projectRequestDto){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        project.update(projectRequestDto.getProjectName());

        projectRepository.save(project);
    }

    @Transactional
    public void deleteById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 projectId 값을 가진 프로젝트 정보가 없습니다."));

        List<MetaDataResponseDto> metaDataList = metaDataService.findByProjectId(projectId);

        for(MetaDataResponseDto metaData : metaDataList){
             metaDataService.deleteById(metaData.getMetadataId());
        }

        projectRepository.delete(project);
    }


}
