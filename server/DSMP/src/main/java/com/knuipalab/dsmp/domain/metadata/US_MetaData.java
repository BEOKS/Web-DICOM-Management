package com.knuipalab.dsmp.domain.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter //lombok , 모든 field 값의 getter 매소드를 자동 생성
@NoArgsConstructor // lombok , 기본 생성자 자동추가
@Document(collection = "US_metadata") //DB에 저장될 document의 이름은 @Documemt 애노테이션을 통하여 지정
public class US_MetaData {

    @Id
    private String _id;

    private String anonymized_id;

    private String age;

    private String modality;

    private String manufacturer;

    private String manufacturerModelName;

    @Field("class\nbenign: 0 malignant: 1")
    private String isMalignant; // malignant(1) : 악성, benign(0) : 양성

    @Builder// lombok,// Builder와 생성자의 역할과 호출 시점은 같다. 다만 차이점으로는 Builder는 어떤 필드에 어떤 값을 채울지 명확히 알 수 있다.
    public US_MetaData(String _id,String anonymized_id, String age, String modality, String manufacturer, String manufacturerModelName, String isMalignant){
        this._id = _id;
        this.anonymized_id = anonymized_id;
        this.age = age;
        this.modality = modality;
        this.manufacturer = manufacturer;
        this.manufacturerModelName = manufacturerModelName;
        this.isMalignant = isMalignant;
    }

    @Override
    public String toString() {
        return "US_MetaData{" +
                "_id=" + _id +
                ", anonymized_id='" + anonymized_id + '\'' +
                ", age='" + age + '\'' +
                ", modality='" + modality + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", manufacturerModelName='" + manufacturerModelName + '\'' +
                ", isMalignant='" + isMalignant + '\'' +
                '}';
    }
}
