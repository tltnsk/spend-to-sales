package com.spendtosales.identity.dto;

import java.util.UUID;

public record RegisterResponse(
        UUID userId,
        UUID businessId,
        String email
) {
}
