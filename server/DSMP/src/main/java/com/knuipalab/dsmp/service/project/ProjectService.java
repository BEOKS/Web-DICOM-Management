package com.knuipalab.dsmp.service.project;

import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.project.ProjectRepository;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Transactional (readOnly = true)
    public List<ProjectResponseDto> findAll(){

        List <ProjectResponseDto> projectResponseDtoList = new ArrayList<ProjectResponseDto>();
        List <Project> projectList = projectRepository.findAll();

        for(Project project: projectList){
            projectResponseDtoList.add(new ProjectResponseDto(project));
        }

        return projectResponseDtoList;
    }

    @Transactional (readOnly = true)
    public ProjectResponseDto findById(String projectId){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 Id값을 가진 프로젝트 정보가 없습니다."));

        return new ProjectResponseDto(project);
    }

    @Transactional
    public void insert(ProjectRequestDto projectRequestDto){

        Project project = new Project().builder()
                .projectName(projectRequestDto.getProjectName())
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void update(String projectId,ProjectRequestDto projectRequestDto){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 Id값을 가진 프로젝트 정보가 없습니다."));

        project.update(projectRequestDto.getProjectName());

        projectRepository.save(project);
    }

    @Transactional
    public void delete(String projectId){

        List<MetaData> metaDataList = metaDataRepository.findByProjectId(projectId);

        metaDataRepository.deleteAll(metaDataList);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->new IllegalArgumentException("해당 Id값을 가진 프로젝트 정보가 없습니다."));

        projectRepository.delete(project);
    }


}