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

    private String userId;

    @Builder
    public Project(String projectId,String projectName, String userId){
        this.projectId = projectId;
        this.projectName = projectName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public void update(String newProjectName){ this.projectName = newProjectName; }

}
