package com.spendtosales.ingestion.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

// MappingRequest.java
public record MappingRequest(
        @NotEmpty Map<String, String> columns,  //  "email" -> "E-mail"
        String dateFormat,                      // "d/M/yyyy" (the default) or "M/d/yyyy"
        String decimalSeparator,                // "," (the default) or "."
        String fileChannel                      // one channel for the whole file, example: "Facebook"
) {
}