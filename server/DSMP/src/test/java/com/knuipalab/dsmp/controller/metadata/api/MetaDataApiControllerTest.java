//package com.knuipalab.dsmp.controller.metadata.api;
//
//import com.knuipalab.dsmp.domain.metadata.MetaData;
//import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
//
//import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.Test;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RunWith(SpringRunner.class)
//@DataMongoTest
//public class MetaDataApiControllerTest {
//
//    String new_anonymized_id = "new_anonymized_id";
//    String new_age = "new_age";
//    String new_modality = "new_modality";
//    String new_manufacturer = "new_manufacturer";
//    String new_manufacturerModelName = "new_manufacturerModelName";
//    String new_isMalignant = "new_isMalignant";
//
//    @Autowired
//    private MetaDataRepository us_metaDataRepository;
//
//    @BeforeEach
//    void init(){
//        this.us_metaDataRepository.deleteAll();
//    }
//
//    void create_MeataData(){ // make 10 random data
//        List<MetaData> new_MetaData_list = new ArrayList<MetaData>();
//        for(int i = 0 ; i < 10 ; i++ ){
//            MetaData us_MetaData = MetaData.builder()
//                    .anonymized_id(new_anonymized_id + i)
//                    .modality(new_modality + i)
//                    .manufacturer(new_manufacturer + i)
//                    .manufacturerModelName(new_manufacturerModelName + i)
//                    .isMalignant(new_isMalignant + i)
//                    .age(new_age + i)
//                    .build();
//            new_MetaData_list.add(us_MetaData);
//        }
//        us_metaDataRepository.saveAll(new_MetaData_list);
//    }
//
//    void assert_MetaData(MetaData us_metaData , int index){
//        MetaDataResponseDto us_metaDataResponseDto = new MetaDataResponseDto(us_metaData);
//        Assertions.assertEquals(new_anonymized_id + index,us_metaDataResponseDto.getAnonymized_id());
//        Assertions.assertEquals(new_age + index , us_metaDataResponseDto.getAge());
//        Assertions.assertEquals(new_isMalignant + index , us_metaDataResponseDto.getIsMalignant());
//        Assertions.assertEquals(new_modality + index , us_metaDataResponseDto.getModality());
//        Assertions.assertEquals(new_manufacturer + index , us_metaDataResponseDto.getManufacturer());
//        Assertions.assertEquals(new_manufacturerModelName + index , us_metaDataResponseDto.getManufacturerModelName());
//    }
//
//    @DisplayName("Mongo create and assert")
//    @Test
//    public void create_assert_test(){
//        create_MeataData();
//        List<MetaData> allMetaData = us_metaDataRepository.findAll();
//        Assertions.assertEquals(10,allMetaData.size()); // size Check
//        int random_index = (int) (Math.random() * 10); ///10 미만 난수 생성
//        assert_MetaData(allMetaData.get(random_index),random_index);
//    }
//
//}
//
