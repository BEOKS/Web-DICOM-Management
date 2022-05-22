package com.knuipalab.dsmp.orthanc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrthancService {

    OrthancRestClient orthancRestClient = new OrthancRestClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public String getPatinetUuidByPatientID(String id) throws IOException {
        // get patient info
        JsonNode response = orthancRestClient.getPatientInfo(id);

        // parsing the response
        ArrayNode arrayNode = (ArrayNode) response;
        Iterator<JsonNode> itr = arrayNode.elements();
        String patientUUID = "-1";
        while( itr.hasNext() ) {
            patientUUID = itr.next().get("ParentPatient").asText();
            break;
        }
        System.out.println(patientUUID);
        return patientUUID;
    }

    public String getStudyUuidByStudyID(String id) throws IOException {
        // get patient info
        JsonNode response = orthancRestClient.getStudyInfo(id);

        // parsing the response
        ArrayNode arrayNode = (ArrayNode) response;
        Iterator<JsonNode> itr = arrayNode.elements();
        String studyUUID = "-1";
        while( itr.hasNext() ) {
            studyUUID = itr.next().get("ID").asText();
            break;
        }
        System.out.println(studyUUID);
        return studyUUID;
    }

    public JsonNode uploadDicom(MultipartFile file) throws IOException{
        return orthancRestClient.uploadDicom(file);
    }

    public JsonNode getStudyListByPatientID(String id) throws IOException {
        // get patient info
        JsonNode response = orthancRestClient.getPatientInfo(id);

        // parsing the response
        ArrayNode arrayNode = (ArrayNode) response;
        List<JsonNode> list = new ArrayList<JsonNode>();

        Iterator<JsonNode> itr = arrayNode.elements();
        while( itr.hasNext() ) {
            Iterator<JsonNode> in_itr = itr.next().get("MainDicomTags").elements();
            while( in_itr.hasNext() ){
                JsonNode field = in_itr.next();
                if (field.get("Name").asText().equals("StudyInstanceUID")){
                    list.add(field.get("Value"));
                    break;
                }
            }
        }
        return objectMapper.valueToTree(list);
    }
    public JsonNode getStudies() throws IOException{
        return orthancRestClient.getStudies();
    }

    public ResponseEntity deleteStudyByStudyUUID(String id) throws IOException{
        return orthancRestClient.deleteStudy(id);
    }

    public ResponseEntity deletePatientByPatientUUID(String id) throws IOException{
        return orthancRestClient.deletePatient(id);
    }

}
