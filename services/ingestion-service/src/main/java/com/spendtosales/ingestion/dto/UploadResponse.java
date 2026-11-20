package com.spendtosales.ingestion.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record UploadResponse(
        UUID uploadId,
        String dataType,
        String fileName,
        int totalRows,
        List<String> headers,
        List<Map<String, String>> preview
) {
}