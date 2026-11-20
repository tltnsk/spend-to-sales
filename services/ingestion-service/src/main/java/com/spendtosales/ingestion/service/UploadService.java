package com.spendtosales.ingestion.service;

import com.spendtosales.ingestion.dto.UploadResponse;
import com.spendtosales.ingestion.entity.StagedRow;
import com.spendtosales.ingestion.entity.Upload;
import com.spendtosales.ingestion.repository.StagedRowRepository;
import com.spendtosales.ingestion.repository.UploadRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService {

    private static final List<String> DATA_TYPES = List.of("AD_SPEND", "SIGNUPS", "SALES");

    private final UploadRepository uploadRepository;
    private final StagedRowRepository stagedRowRepository;

    public UploadService(UploadRepository uploadRepository, StagedRowRepository stagedRowRepository) {
        this.uploadRepository = uploadRepository;
        this.stagedRowRepository = stagedRowRepository;
    }

    // either the upload and all its rows are saved together or nothing is saved
    // to prevent inconsistencies
    @Transactional
    public UploadResponse upload(MultipartFile file, String dataType, UUID businessId, UUID userId) {
        if (!DATA_TYPES.contains(dataType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "dataType must be AD_SPEND, SIGNUPS or SALES");
        }
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file is empty");
        }

        String text = readText(file);

        List<String> headers = new ArrayList<>();
        List<Map<String, String>> rows = new ArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(detectDelimiter(text))
                .setHeader()                // the first line has the column names
                .setSkipHeaderRecord(true)  // don't return the header line
                .get();

        try (CSVParser parser = CSVParser.parse(text, format)) {
            headers.addAll(parser.getHeaderNames());
            for (CSVRecord record : parser) {
                rows.add(record.toMap());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not read the file as CSV: " + e.getMessage());
        }

        // two columns with the same name would overwrite each other
        if (new HashSet<>(headers).size() != headers.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every column must have a different name");
        }

        if (rows.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file has no rows");
        }

        Upload upload = new Upload(businessId, userId, dataType, file.getOriginalFilename(),
                headers, rows.size());
        upload = uploadRepository.save(upload);

        List<StagedRow> stagedRows = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            int rowNumber = i + 2; // row 1 is the header
            stagedRows.add(new StagedRow(upload.getId(), rowNumber, rows.get(i)));
        }
        stagedRowRepository.saveAll(stagedRows);

        // show the first 5 rows so the owner can check the file looks right
        List<Map<String, String>> preview = rows.subList(0, Math.min(5, rows.size()));

        return new UploadResponse(upload.getId(), dataType, upload.getFileName(),
                rows.size(), headers, preview);
    }

    private String readText(MultipartFile file) {
        try {
            String text = new String(file.getBytes(), StandardCharsets.UTF_8);
            // Excel sometimes puts an invisible character (BOM) at the start of the file
            if (text.startsWith("\uFEFF")) {
                text = text.substring(1);
            }
            return text;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read the file");
        }
    }

    // excel can use either ";" or "," between columns
    private char detectDelimiter(String text) {
        String firstLine = text.lines().findFirst().orElse("");

        int semicolons = 0;
        int commas = 0;
        for (char c : firstLine.toCharArray()) {
            if (c == ';') {
                semicolons++;
            } else if (c == ',') {
                commas++;
            }
        }

        if (semicolons > commas) {
            return ';';
        }
        return ',';
    }
}