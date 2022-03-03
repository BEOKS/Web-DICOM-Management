package com.knuipalab.dsmp.dto.project;


import com.knuipalab.dsmp.domain.project.Project;
import com.knuipalab.dsmp.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectResponseDto {

    private String projectId;

    private String projectName;

    private User creator;

    private List<User> visitor;

    public ProjectResponseDto(Project entity){
        this.projectId = entity.getProjectId();
        this.projectName = entity.getProjectName();
        this.creator = entity.getCreator();
        this.visitor = entity.getVisitor();
    }
}
