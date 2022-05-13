package com.knuipalab.dsmp.project;


import com.knuipalab.dsmp.user.User;
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
