package com.knuipalab.dsmp.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuipalab.dsmp.metadata.domain.MetaData;
import com.knuipalab.dsmp.metadata.dto.MetaDataCreateAllRequestDto;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MockMetaData {

    MetaData mockedMetaData;
    List<MetaData> mockedMetaDataList;

    public MockMetaData(){
        this.mockedMetaData = getMockMetaData();
        this.mockedMetaDataList = getMockMetaDataList();
    }

    private static MetaData getMockMetaData(){

        String metaId = "12345";
        String proId = "54321";
        String strBody = "{\n" +
                "   \"stored_dicom_id\": 145125,\n" +
                "    \"anonymized_id\": 1028011,\n" +
                "    \"age\": 53,\n" +
                "    \"modality\": \"MG\",\n" +
                "    \"manufacturer\": \"HOLOGIC, Inc.\",\n" +
                "    \"manufacturerModelName\": \"Lorad Selenia\",\n" +
                "    \"class non-pCR: 0 pCR: 1\": 0,\n" +
                "    \"left: 0 right: 1\": 1,\n" +
                "    \"ER\": 1,\n" +
                "    \"PR\": 1,\n" +
                "    \"HER2\": 1,\n" +
                "    \"non-IDC: 0\\nIDC: 1\": 1,\n" +
                "    \"compressionForce\": 173.5019\n" +
                "  }";

        Bson body = Document.parse(strBody);

        MetaData metaData = MetaData.builder()
                .metadataId(metaId)
                .projectId(proId)
                .body(body)
                .build();

        return metaData;
    }

    private static List<Document> convertToDocument(String strBodyList) {

        ObjectMapper mapper = new ObjectMapper();
        List<Document> bsonList = null;
        try {
            bsonList = mapper.readValue(strBodyList, new TypeReference<List<Document>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bsonList;
    }

    private static List<MetaData> getMockMetaDataList(){

        String metaId = "12345";
        String proId = "54321";
        String strBodyList = "[{\n" +
                "   \"stored_dicom_id\": 145125,\n" +
                "    \"anonymized_id\": 1028011,\n" +
                "    \"age\": 53,\n" +
                "    \"modality\": \"MG\",\n" +
                "    \"manufacturer\": \"HOLOGIC, Inc.\",\n" +
                "    \"manufacturerModelName\": \"Lorad Selenia\",\n" +
                "    \"class non-pCR: 0 pCR: 1\": 0,\n" +
                "    \"left: 0 right: 1\": 1,\n" +
                "    \"ER\": 1,\n" +
                "    \"PR\": 1,\n" +
                "    \"HER2\": 1,\n" +
                "    \"non-IDC: 0\\nIDC: 1\": 1,\n" +
                "    \"compressionForce\": 173.5019\n" +
                "  },\n" +
                "  {\n" +
                "   \"stored_dicom_id\": 145126,\n" +
                "    \"anonymized_id\": 1028012,\n" +
                "    \"age\": 54,\n" +
                "    \"modality\": \"MG\",\n" +
                "    \"manufacturer\": \"HOLOGIC, Inc.\",\n" +
                "    \"manufacturerModelName\": \"Lorad Selenia\",\n" +
                "    \"class non-pCR: 0 pCR: 1\": 0,\n" +
                "    \"left: 0 right: 1\": 1,\n" +
                "    \"ER\": 1,\n" +
                "    \"PR\": 1,\n" +
                "    \"HER2\": 1,\n" +
                "    \"non-IDC: 0\\nIDC: 1\": 1,\n" +
                "    \"compressionForce\": 173.5019\n" +
                "  }]";

        List<MetaData> metaDataList = new ArrayList<>();

        MetaDataCreateAllRequestDto metaDataCreateAllRequestDto = new MetaDataCreateAllRequestDto(proId,convertToDocument(strBodyList));
        List<Document>bodyList = metaDataCreateAllRequestDto.getBodyList();
        String projectId = metaDataCreateAllRequestDto.getProjectId();

        for(Document body : bodyList) {
            MetaData metaData = new MetaData().builder()
                    .metadataId(metaId)
                    .projectId(projectId)
                    .body(body).build();
            metaDataList.add(metaData);
            metaId = String.valueOf((Integer.parseInt(metaId)+1));
        }

        return metaDataList;
    }
}
