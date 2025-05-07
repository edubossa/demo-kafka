package com.demo.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private String customerId;
    private BigDecimal amount;
    private String merchantName;
    private String cardNumber;
    private LocalDateTime timestamp;
    private String location;
}
