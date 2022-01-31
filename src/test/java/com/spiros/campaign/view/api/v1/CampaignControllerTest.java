package com.spiros.campaign.view.api.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CampaignControllerTest extends SampleCsvFileLoaderForApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveAllCampaignsByCampaignGroupId() throws Exception {

        loadSampleCsvFile();

        mockMvc.perform(get("/api/v1/campaign/all/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]name", is("campaign1")))
                .andExpect(jsonPath("$[1]name", is("campaign2")))
                .andExpect(jsonPath("$[2]name", is("campaign3")))
                .andExpect(jsonPath("$[3]name", is("campaign4")))
                .andExpect(jsonPath("$[4]name", is("campaign5")));

    }
}