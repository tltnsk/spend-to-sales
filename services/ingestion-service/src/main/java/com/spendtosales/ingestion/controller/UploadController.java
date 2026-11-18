package com.spendtosales.ingestion.controller;

import com.spendtosales.ingestion.dto.UploadResponse;
import com.spendtosales.ingestion.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadResponse upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("dataType") String dataType,
                                 @AuthenticationPrincipal Jwt jwt) {
        // who is uploading
        UUID businessId = UUID.fromString(jwt.getClaimAsString("businessId"));
        UUID userId = UUID.fromString(jwt.getSubject());

        return uploadService.upload(file, dataType, businessId, userId);
    }
}