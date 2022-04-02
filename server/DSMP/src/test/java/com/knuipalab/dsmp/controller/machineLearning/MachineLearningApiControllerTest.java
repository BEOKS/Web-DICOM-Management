package com.knuipalab.dsmp.controller.machineLearning;

import com.knuipalab.dsmp.configuration.auth.CustomOAuth2UserService;
import com.knuipalab.dsmp.controller.machineLearning.api.MachineLearningApiController;
import com.knuipalab.dsmp.service.machineLearning.AsyncMetaDataSampler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MachineLearningApiController.class)
public class MachineLearningApiControllerTest {

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    private AsyncMetaDataSampler asyncMetaDataSampler;

    @WithMockUser
    @DisplayName("Type Sampling Test by ProjectId - Success")
    @Test
    void typeSamplingTest() throws Exception {

        mvc.perform(put("/api/MetaData/Sampling/54321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
        ;
    }

    @WithMockUser
    @DisplayName("Set Malignancy Classification Test by ProjectId - Success")
    @Test
    void setMalignancyClassificationTest() throws Exception {

        mvc.perform(put("/api/MetaData/MalignancyClassification/54321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(200)))
                .andDo(print())
        ;
    }

}
