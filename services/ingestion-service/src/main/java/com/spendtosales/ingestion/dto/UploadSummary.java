package com.spendtosales.ingestion.dto;

import java.time.Instant;
import java.util.UUID;

public record UploadSummary(
        UUID uploadId,
        String fileName,
        String dataType,
        String status,
        int totalRows,
        Integer validRows,
        Integer rejectedRows,
        Instant createdAt
) {
}