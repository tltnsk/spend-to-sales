package com.spendtosales.identity.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "business")
public class Business {

    @Id                                          // the primary key
    @GeneratedValue(strategy = GenerationType.UUID)  // Java generates the id
    private UUID id;

    private String name;

    @JdbcTypeCode(SqlTypes.CHAR) // the column is char(3)
    @Column(length = 3)
    private String currency = "EUR";

    private String timezone = "Europe/Sofia";

    private Instant createdAt = Instant.now();

    protected Business() {}      // empty constructor

    public Business(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getCurrency() { return currency; }
    public String getTimezone() { return timezone; }
    public Instant getCreatedAt() { return createdAt; }
}