package com.spendtosales.ingestion.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Entity
@Table(name = "upload")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID businessId;

    private UUID uploadedBy;

    private String dataType;

    private String fileName;

    private String status;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> headers;

    private int totalRows;

    private Instant createdAt = Instant.now();

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> mapping;

    private String dateFormat;

    private String decimalSeparator;

    private String fileChannel;

    private Integer validRows;

    private Integer rejectedRows;

    protected Upload() {}

    public Upload(UUID businessId, UUID uploadedBy, String dataType, String fileName,
                  List<String> headers, int totalRows) {
        this.businessId = businessId;
        this.uploadedBy = uploadedBy;
        this.dataType = dataType;
        this.fileName = fileName;
        this.headers = headers;
        this.totalRows = totalRows;
        this.status = "UPLOADED";
    }

    // remember how the file was read and how many rows passed the cleaning
    public void saveMapping(Map<String, String> mapping, String dateFormat, String decimalSeparator,
                            String fileChannel, int validRows, int rejectedRows) {
        this.mapping = mapping;
        this.dateFormat = dateFormat;
        this.decimalSeparator = decimalSeparator;
        this.fileChannel = fileChannel;
        this.validRows = validRows;
        this.rejectedRows = rejectedRows;
        this.status = "MAPPED";
    }

    public void markImported() {
        this.status = "IMPORTED";
    }

    public UUID getId() { return id; }
    public UUID getBusinessId() { return businessId; }
    public UUID getUploadedBy() { return uploadedBy; }
    public String getDataType() { return dataType; }
    public String getFileName() { return fileName; }
    public String getStatus() { return status; }
    public List<String> getHeaders() { return headers; }
    public int getTotalRows() { return totalRows; }
    public Instant getCreatedAt() { return createdAt; }
    public Map<String, String> getMapping() { return mapping; }
    public String getDateFormat() { return dateFormat; }
    public String getDecimalSeparator() { return decimalSeparator; }
    public String getFileChannel() { return fileChannel; }
    public Integer getValidRows() { return validRows; }
    public Integer getRejectedRows() { return rejectedRows; }
}