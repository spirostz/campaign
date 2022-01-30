package com.spiros.campaign.view.api.v1;

import com.spiros.campaign.core.api.CsvHandlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/csv")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);


    @Autowired
    private CsvHandlingService csvHandlingService;

    @PostMapping("/upload/campaignGroup/name/{campaignGroupName}")
    public ResponseEntity<Map<String, String>> handleCsvFileUpload(
            @RequestParam("file") MultipartFile file,
            @PathVariable String campaignGroupName
    ) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("campaignGroup", "Name: " + campaignGroupName);

        try (InputStream inStream = file.getInputStream()) {

            csvHandlingService.handleCsvFile(inStream, campaignGroupName);

            result.put("msg", "Submitted File: " + file.getOriginalFilename());
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("Problem uploading file", e);
            result.put("msg", "Problem uploading file " + e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
