package com.knuipalab.dsmp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProjectRequestDto {

    private String projectName;

    public ProjectRequestDto(String projectName){
        this.projectName = projectName;
    }
}
