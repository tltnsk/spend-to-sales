package com.spendtosales.ingestion.dto;

import java.util.List;
import java.util.UUID;

public record MappingResponse(
        UUID uploadId,
        int validRows,
        int rejectedRows,
        List<RowError> errors   // rejected rows
) {
}