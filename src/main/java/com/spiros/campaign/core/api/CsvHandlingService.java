package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.logic.ImportCampaignProcessService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvHandlingService {

    public static final BigDecimal INITIAL_VALUE_OF_REVENUE_INSIDE_CAMPAIGN = BigDecimal.ZERO;

    @Autowired
    private ImportCampaignProcessService importCampaignProcessService;

    public void handleCsvFile(InputStream inStream, String campaignGroupName) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

        //TODO: validate that title exist and its correct

        List<Campaign> campaigns = csvParser.stream()
                .skip(1) //Skip title
                .map(this::mapCsvRecordToCampaign )
                .collect(Collectors.toList());

        importCampaignProcessService.processDataFromCsv(campaigns, campaignGroupName);
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
