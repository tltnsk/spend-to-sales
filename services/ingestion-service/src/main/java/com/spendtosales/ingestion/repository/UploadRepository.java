package com.spendtosales.ingestion.repository;

import com.spendtosales.ingestion.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface UploadRepository extends JpaRepository<Upload, UUID> {
    // all uploads of one business, newest first
    List<Upload> findByBusinessIdOrderByCreatedAtDesc(UUID businessId);
}