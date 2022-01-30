package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.logic.ImportCampaignProcessService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CsvHandlingServiceTest {

    @InjectMocks
    private CsvHandlingService csvHandlingService;

    @Mock
    private ImportCampaignProcessService importCampaignProcessService;

    @Test
    void handleCsvFile_givenOriginalFile_shouldConvertedToCampaignsList() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("campaigns.csv")).getFile());
        InputStream inStream = new FileInputStream(file);

        csvHandlingService.handleCsvFile(inStream, "name1");

        List<Campaign> expectedCampaignList = Arrays.asList(
                createExpectedCampaign("2021-July-BOF-Books", "2108", 36358L),
                createExpectedCampaign("test", "2108", 36358L),
                createExpectedCampaign("3_299_BBQ_G-A_CV_SHP", "674", 29980L),
                createExpectedCampaign("3_299_Bulbs_G-A_CV_SHP", "2000", 57561L),
                createExpectedCampaign("3_299_Containers_G-A_OT_SHP", "500", 25864L),
                createExpectedCampaign("3_299_Furniture_G-A_CV_SHP", "1023", 68640L),
                createExpectedCampaign("3_299_Gifts_AOC_G-A_OT_SHP", "500", 32743L),
                createExpectedCampaign("3_299_Lawn_Care_G-A_CV_SHP", "4600", 31023L),
                createExpectedCampaign("3_299_Vegepod_G-A_CV_SHP", "1325", 15209L),
                createExpectedCampaign("3_299_Wild_Bird_G-A_AOC_SHP", "500", 4931L),
                createExpectedCampaign("Optily-July2021-TOF-Test", "1", 0L)
        );

        verify(importCampaignProcessService, times(1)).processDataFromCsv(expectedCampaignList, "name1");

    }

    private Campaign createExpectedCampaign(String name, String budget, Long impressions) {
        Campaign campaign = new Campaign();
        campaign.setName(name);
        campaign.setBudget(new BigDecimal(budget));
        campaign.setImpressions(impressions);
        campaign.setRevenue(BigDecimal.ZERO);
        return campaign;
    }
}