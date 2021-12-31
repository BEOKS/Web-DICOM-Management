package com.knuipalab.dsmp.dto.metadata;

import com.knuipalab.dsmp.domain.metadata.US_MetaData;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
public class US_MetaDataResponseDto {

    private String _id;

    private String anonymized_id;

    private String age;

    private String modality;

    private String manufacturer;

    private String manufacturerModelName;

    private String isMalignant; // malignant(1) : 악성, benign(0) : 양성

    public US_MetaDataResponseDto(US_MetaData entity){
        this._id = entity.get_id();
        this.anonymized_id = entity.getAnonymized_id();
        this.age = entity.getAge();
        this.modality = entity.getModality();
        this.manufacturer = entity.getManufacturer();
        this.manufacturerModelName = entity.getManufacturerModelName();
        this.isMalignant = entity.getIsMalignant();
    }
}
