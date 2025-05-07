package com.demo.kafka.controller;

import com.demo.kafka.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        // Complementar dados da transação
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID().toString());
        }
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }

        // Enviar para o Kafka
        kafkaTemplate.send("financial-transactions", transaction.getCustomerId(), transaction);

        return transaction;
    }

}