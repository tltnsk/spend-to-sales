package com.spendtosales.ingestion.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WhoAmIController {

    // temporary test endpoint to show who is calling
    @GetMapping("/api/uploads/whoami")
    public Map<String, String> whoAmI(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        String businessId = jwt.getClaimAsString("businessId");

        return Map.of(
                "userId", userId,
                "businessId", businessId
        );
    }
}