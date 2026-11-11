package com.spendtosales.identity.service;

import com.spendtosales.identity.dto.RegisterRequest;
import com.spendtosales.identity.dto.RegisterResponse;
import com.spendtosales.identity.entity.AppUser;
import com.spendtosales.identity.entity.Business;
import com.spendtosales.identity.repository.AppUserRepository;
import com.spendtosales.identity.repository.BusinessRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationService {

    private final BusinessRepository businessRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(BusinessRepository businessRepository,
                               AppUserRepository appUserRepository,
                               PasswordEncoder passwordEncoder) {
        this.businessRepository = businessRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // both saves succeed, or neither does
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // normalize the email
        var email = request.email().trim().toLowerCase();

        // throw conflict if email already exists
        if (appUserRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        var business = new Business(request.businessName());
        business = businessRepository.save(business);

        var passwordHash = passwordEncoder.encode(request.password());

        var user = new AppUser(business.getId(), email, passwordHash, request.fullName(), "OWNER");

        user = appUserRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                business.getId(),
                user.getEmail()
        );
    }
}