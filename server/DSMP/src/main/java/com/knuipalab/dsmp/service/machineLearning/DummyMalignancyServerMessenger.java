package com.knuipalab.dsmp.service.machineLearning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.StreamSupport;

@Service
public class DummyMalignancyServerMessenger implements MalignancyServerMessenger{
    @Override
    public JsonNode getClassificationData(String instanceID) {
        String strJsonNodeArray = "[{\n" +
                "   \"classification1\": 120,\n" +
                "    \"classification2\": 50\n" +
                "  },\n" +
                "  {\n" +
                "   \"classification1\": 110,\n" +
                "    \"classification2\": 80\n" +
                "  }]";
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=null;
        try {
             jsonNode = objectMapper.readTree(strJsonNodeArray);
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
