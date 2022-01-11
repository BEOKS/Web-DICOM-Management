package com.knuipalab.dsmp.domain.project;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "project")
public class Project {

    @Id
    private String projectId;

    private String projectName;

    @Builder
    public Project(String projectId,String projectName){
        this.projectId = projectId;
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    public void update(String newProjectName){ this.projectName = newProjectName; }

}
