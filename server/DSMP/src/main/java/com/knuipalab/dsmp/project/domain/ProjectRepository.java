package com.knuipalab.dsmp.project.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project,String> {

    @Query("{'creator._id' : ?0}")
    List<Project> findByCreator(ObjectId creatorId);

    @Query("{'visitor' : { $elemMatch: { _id : ?0 } }}")
    List<Project> findInvisitedProject(ObjectId userId);
}
