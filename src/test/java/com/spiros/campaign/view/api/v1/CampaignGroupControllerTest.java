package com.spiros.campaign.view.api.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CampaignGroupControllerTest extends SampleCsvFileLoaderForApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveAllCampaignGroups() throws Exception {

        String groupId = loadSampleCsvFile();

        mockMvc.perform(get("/api/v1/campaignGroup/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]name", is("testGroup1")))
                .andExpect(jsonPath("$[0]id", is(Integer.parseInt(groupId))));
    }

    @Test
    void handleCsvFileUpload() throws Exception {

        MockMultipartFile file = prepareSampleFile();

        mockMvc.perform(multipart("/api/v1/campaignGroup/uploadCsv/testGroup1")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("campaignGroupName", is("testGroup1")))
                .andExpect(jsonPath("filename", is("campaignGroup.csv")));
    }



}