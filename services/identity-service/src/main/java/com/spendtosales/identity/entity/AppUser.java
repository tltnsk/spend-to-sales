package com.spendtosales.identity.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Column(length = 200)
    private String fullName;

    @Column(nullable = false, length = 20)
    private String role;

    private Instant createdAt = Instant.now();

    protected AppUser() {}

    public AppUser(UUID businessId, String email, String passwordHash, String fullName, String role) {
        this.businessId = businessId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
    }

    public UUID getId() {return id;}
    public UUID getBusinessId() {return businessId;}
    public String getEmail() {return email;}
    public String getPasswordHash() {return passwordHash;}
    public String getFullName() {return fullName;}
    public String getRole() {return role;}
    public Instant getCreatedAt() {return createdAt;}
}
