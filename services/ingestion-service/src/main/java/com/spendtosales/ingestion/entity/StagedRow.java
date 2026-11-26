package com.spendtosales.ingestion.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "staged_row")
public class StagedRow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID uploadId;

    private int rowNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> raw;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> normalized;

    private String status;

    private String error;

    protected StagedRow() {}

    public StagedRow(UUID uploadId, int rowNumber, Map<String, String> raw) {
        this.uploadId = uploadId;
        this.rowNumber = rowNumber;
        this.raw = raw;
        this.status = "PENDING";
    }

    public void markValid(Map<String, String> normalized) {
        this.normalized = normalized;
        this.status = "VALID";
        this.error = null;
    }

    public void markRejected(String error) {
        this.normalized = null;
        this.status = "REJECTED";
        this.error = error;
    }

    public UUID getId() { return id; }
    public UUID getUploadId() { return uploadId; }
    public int getRowNumber() { return rowNumber; }
    public Map<String, String> getRaw() { return raw; }
    public Map<String, String> getNormalized() { return normalized; }
    public String getStatus() { return status; }
    public String getError() { return error; }
}