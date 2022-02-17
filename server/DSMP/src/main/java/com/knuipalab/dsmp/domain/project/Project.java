package com.knuipalab.dsmp.domain.project;


import com.knuipalab.dsmp.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@Document(collection = "project")
public class Project {

    @Id
    private String projectId;

    private String projectName;

    private User creator;

    private List<User> visitor;

    @Builder
    public Project(String projectId,String projectName, User creator){
        this.projectId = projectId;
        this.projectName = projectName;
        this.creator = creator;
        this.visitor = new ArrayList<User>();
    }

    public void update(String newProjectName){ this.projectName = newProjectName; }

    public void invite(List<User> userList){
        this.visitor = Stream.of(this.visitor,userList)
                .flatMap( visitor -> visitor.stream())
                .collect(Collectors.toList());
    }

}
