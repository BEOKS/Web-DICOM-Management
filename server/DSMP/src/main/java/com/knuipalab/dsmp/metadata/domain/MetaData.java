package com.knuipalab.dsmp.metadata.domain;

import com.knuipalab.dsmp.util.bson.BsonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.conversions.Bson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter //lombok , 모든 field 값의 getter 매소드를 자동 생성
@NoArgsConstructor // lombok , 기본 생성자 자동추가
@Document(collection = "metadata") //DB에 저장될 document의 이름은 @Documemt 애노테이션을 통하여 지정
public class MetaData {

    @Id
    private String metadataId;

    private String projectId;

    private Bson body;

    @Builder// lombok,// Builder와 생성자의 역할과 호출 시점은 같다. 다만 차이점으로는 Builder는 어떤 필드에 어떤 값을 채울지 명확히 알 수 있다.
    public MetaData(String metadataId, String projectId , Bson body){
        this.metadataId = metadataId;
        this.projectId = projectId;
        this.body = body;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "metadataId='" + metadataId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", body=" + body +
                '}';
    }

    public void update(Bson newBody){
        this.body = newBody;
    }

    public String getPatientIdFromBody(){
        Bson body = this.body;
        BsonUtils bsonUtils = new BsonUtils();
        String patientId = bsonUtils.toJavaType(body.toBsonDocument().get("anonymized_id")).toString();
        return patientId;
    }

    public String getImageNameFromBody(){
        Bson body = this.body;
        BsonUtils bsonUtils = new BsonUtils();
        String imageName = bsonUtils.toJavaType(body.toBsonDocument().get("image_name")).toString();
        return imageName;
    }

}
