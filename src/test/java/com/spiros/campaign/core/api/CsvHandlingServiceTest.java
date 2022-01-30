package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.logic.ImportCampaignProcessService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        InputStream inStream = readCsvFileAsInputStream("campaigns.csv");

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

        verify(importCampaignProcessService, times(1)).processIncomingData(expectedCampaignList, "name1");

    }

    @Test
    void handleCsvFile_givenFileWithDecimalsInBudget_shouldConvertedToCampaignsList() throws IOException {
        InputStream inStream = readCsvFileAsInputStream("campaigns_with_decimals.csv");

        csvHandlingService.handleCsvFile(inStream, "name2");

        List<Campaign> expectedCampaignList = Arrays.asList(
                createExpectedCampaign("2021-July-BOF-Books", "2108.8962", 36358L),
                createExpectedCampaign("test", "2108.12345678987654321", 36358L),
                createExpectedCampaign("3_299_BBQ_G-A_CV_SHP", "0.00000000001", 29980L)
        );

        verify(importCampaignProcessService, times(1)).processIncomingData(expectedCampaignList, "name2");

    }

    @Test
    void handleCsvFile_givenFileWithInvalidTitles_shouldFail() throws IOException {
        InputStream inStream = readCsvFileAsInputStream("campaigns_invalid_no_title.csv");

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> csvHandlingService.handleCsvFile(inStream, "nameTest"),
                "IllegalArgumentException error was expected");
        assertEquals("Csv title validation failed", exception.getMessage());

        InputStream inStream2 = readCsvFileAsInputStream("campaigns_invalid_wrong_title.csv");
        IllegalArgumentException exception2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> csvHandlingService.handleCsvFile(inStream2, "nameTest"),
                "IllegalArgumentException error was expected");
        assertEquals("Csv title validation failed", exception2.getMessage());

    }

    @NotNull
    private InputStream readCsvFileAsInputStream(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return new FileInputStream(file);
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