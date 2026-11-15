package com.spendtosales.identity.controller;

import com.spendtosales.identity.dto.LoginRequest;
import com.spendtosales.identity.dto.LoginResponse;
import com.spendtosales.identity.dto.RegisterRequest;
import com.spendtosales.identity.dto.RegisterResponse;
import com.spendtosales.identity.entity.AppUser;
import com.spendtosales.identity.service.JwtService;
import com.spendtosales.identity.service.LoginService;
import com.spendtosales.identity.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final JwtService jwtService;

    public AuthController(RegistrationService registrationService, LoginService loginService, JwtService jwtService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return registrationService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        AppUser user= loginService.login(
                request.email(),
                request.password()
        );

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
