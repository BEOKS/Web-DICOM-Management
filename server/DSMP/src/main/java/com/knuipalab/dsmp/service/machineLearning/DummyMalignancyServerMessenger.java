package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DummyMalignancyServerMessenger implements MalignancyServerMessenger{

    @Override
    public JsonNode getClassificationData(String instanceID) {
        String strJsonNode = "{\n" +
                "   \"classification1\": 11.12,\n" +
                "    \"classification2\": 57.8\n" +
                "  }";
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=null;
        try {
             jsonNode = objectMapper.readTree(strJsonNode);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    @Override
    public JsonNode getSegmentationData(String instanceID) {
        return null;
    }

    @Override
    public boolean isServerAvailable() {
        return false;
    }
}
