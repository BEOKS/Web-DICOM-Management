package com.knuipalab.dsmp.dto.project;


import com.knuipalab.dsmp.domain.project.Project;
import lombok.Getter;

@Getter
public class ProjectResponseDto {

    private String projectId;

    private String projectName;

    public ProjectResponseDto(Project entity){
        this.projectId = entity.getProjectId();
        this.projectName = entity.getProjectName();
    }
}
