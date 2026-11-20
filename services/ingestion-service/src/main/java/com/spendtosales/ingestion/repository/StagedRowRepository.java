package com.spendtosales.ingestion.repository;

import com.spendtosales.ingestion.entity.StagedRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StagedRowRepository extends JpaRepository<StagedRow, UUID> {
}