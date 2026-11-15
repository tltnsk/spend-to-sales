package com.spendtosales.identity.service;

import com.spendtosales.identity.entity.AppUser;
import com.spendtosales.identity.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser login(String email, String password) {
        AppUser user = appUserRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password."
                ));
        if (!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid email or password.");
        }
        return user;
    }

}
