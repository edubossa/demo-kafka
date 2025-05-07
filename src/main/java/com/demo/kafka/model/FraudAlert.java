package com.demo.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlert {
    private String customerId;
    private String alertType;
    private String description;
    private LocalDateTime detectionTime;
    private Transaction suspiciousTransaction;
    private int transactionCount;
}

