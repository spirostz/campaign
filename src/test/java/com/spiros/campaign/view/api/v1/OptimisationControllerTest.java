package com.spiros.campaign.view.api.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OptimisationControllerTest extends SampleCsvFileLoaderForApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveLatestOptimisationsByCampaignGroupId() throws Exception {

        loadSampleCsvFile();

        mockMvc.perform(get("/api/v1/optimisation/all/1")
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

    @Test
    void retrieveAllRecommendationsByCampaignGroupIdIfNotApplied() throws Exception {

        loadSampleCsvFile();

        mockMvc.perform(get("/api/v1/optimisation/recommendations/allNotApplied/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]recommendedBudget", is(10.0)))
                .andExpect(jsonPath("$[1]recommendedBudget", is(20.0)))
                .andExpect(jsonPath("$[2]recommendedBudget", is(40.0)))
                .andExpect(jsonPath("$[3]recommendedBudget", is(20.0)))
                .andExpect(jsonPath("$[4]recommendedBudget", is(10.0)));

        applyRecommendations();

        //Already applied
        mockMvc.perform(get("/api/v1/optimisation/recommendations/allNotApplied/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));


    }

    @Test
    void applyOptimisationIfApplicable() throws Exception {

        loadSampleCsvFile();

        mockMvc.perform(get("/api/v1/optimisation/recommendations/apply/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("recommendationApplied", is(true)));

        //Already applied
        mockMvc.perform(get("/api/v1/optimisation/recommendations/apply/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("recommendationApplied", is(false)));
    }

    private void applyRecommendations() throws Exception {
        mockMvc.perform(get("/api/v1/optimisation/recommendations/apply/1")
                .contentType(MediaType.APPLICATION_JSON));
    }
}