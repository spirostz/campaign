package com.spiros.campaign.view.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleCsvFileLoaderForApiTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @NotNull
    String loadSampleCsvFile() throws Exception {
        ResultActions result = mockMvc.perform(multipart("/api/v1/campaignGroup/uploadCsv/testGroup1")
                .file(prepareSampleFile())
        );
        Map<String, String> map = objectMapper
                .readValue(result.andReturn().getResponse().getContentAsString(), Map.class);

        return map.get("campaignGroupId");
    }

    @NotNull
    MockMultipartFile prepareSampleFile() {
        String csvFileData =
                "name,budget,impressions\n" +
                        "campaign1,20,100\n" +
                        "campaign2,20,200\n" +
                        "campaign3,20,400\n" +
                        "campaign4,20,200\n" +
                        "campaign5,20,100";

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "campaignGroup.csv",
                MediaType.TEXT_PLAIN_VALUE,
                csvFileData.getBytes()
        );
        return file;
    }
}
