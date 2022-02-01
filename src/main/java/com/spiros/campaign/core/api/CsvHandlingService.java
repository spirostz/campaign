package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.logic.ImportCampaignProcessService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvHandlingService {

    public static final BigDecimal INITIAL_VALUE_OF_REVENUE_INSIDE_CAMPAIGN = null;
    public static final String TITLE_NAME = "name";
    public static final String TITLE_BUDGET = "budget";
    public static final String TITLE_IMPRESSIONS = "impressions";

    @Autowired
    private ImportCampaignProcessService importCampaignProcessService;

    public Long handleCsvFile(InputStream inStream, String campaignGroupName) throws IOException {
        List<CSVRecord> csvRecords = readCsvRecordsFromInputStream(inStream);

        if (!validateThatTitleExistsAndIsCorrect(csvRecords)) {
            throw new IllegalArgumentException("Csv title validation failed");
        }

        List<Campaign> campaigns = readCsvDataExceptTitleAndMapToCampaigns(csvRecords);

        return importCampaignProcessService.processIncomingData(campaigns, campaignGroupName);

    }

    private List<CSVRecord> readCsvRecordsFromInputStream(InputStream inStream) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
        return csvParser.getRecords();
    }

    private boolean validateThatTitleExistsAndIsCorrect(List<CSVRecord> csvRecords) {
        CSVRecord titleRecord = csvRecords.get(0);
        String name = titleRecord.get(0);
        String budget = titleRecord.get(1);
        String impressions = titleRecord.get(2);

        return name.equals(TITLE_NAME)
                &&
                budget.equals(TITLE_BUDGET)
                &&
                impressions.equals(TITLE_IMPRESSIONS);
    }

    @NotNull
    private List<Campaign> readCsvDataExceptTitleAndMapToCampaigns(List<CSVRecord> csvRecords) {
        return csvRecords.stream()
                .skip(1)
                .map(this::mapCsvRecordToCampaign)
                .collect(Collectors.toList());
    }

    private Campaign mapCsvRecordToCampaign(CSVRecord csvRecord) {
        String name = csvRecord.get(0);
        BigDecimal budget = new BigDecimal(csvRecord.get(1));
        Long impressions = Long.parseLong(csvRecord.get(2));

        Campaign campaign = new Campaign();
        campaign.setName(name);
        campaign.setBudget(budget);
        campaign.setImpressions(impressions);

        campaign.setRevenue(INITIAL_VALUE_OF_REVENUE_INSIDE_CAMPAIGN);
        return campaign;
    }

}
