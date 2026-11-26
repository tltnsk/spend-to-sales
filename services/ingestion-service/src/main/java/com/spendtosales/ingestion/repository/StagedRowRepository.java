package com.spendtosales.ingestion.repository;

import com.spendtosales.ingestion.entity.StagedRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface StagedRowRepository extends JpaRepository<StagedRow, UUID> {
    // all rows of one upload, in file order
    List<StagedRow> findByUploadIdOrderByRowNumber(UUID uploadId);

    // only the rows with this status (e.g. VALID), in file order
    List<StagedRow> findByUploadIdAndStatusOrderByRowNumber(UUID uploadId, String status);
}