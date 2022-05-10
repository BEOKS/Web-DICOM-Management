package com.knuipalab.dsmp.service.machineLearning;

import com.knuipalab.dsmp.machineLearning.service.BasicMalignancyServerMessenger;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class TorchServeTest {
    @SpyBean
    BasicMalignancyServerMessenger basicMalignancyServerMessenger;
    @Test
    public void test12(){
        basicMalignancyServerMessenger.requestMalignancyInference("624e58d849d5a375a491fef7","c_3503506123_n.jpg","");
    }
}
