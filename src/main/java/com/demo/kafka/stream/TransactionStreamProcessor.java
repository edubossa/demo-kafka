package com.demo.kafka.stream;

import com.demo.kafka.model.FraudAlert;
import com.demo.kafka.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * ### Implementação da Stream de Processamento
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionStreamProcessor {

    private static final String TRANSACTIONS_TOPIC = "financial-transactions";
    private static final String FRAUD_ALERTS_TOPIC = "fraud-alerts";

    private final JsonSerde<Transaction> transactionSerde;
    private final JsonSerde<FraudAlert> fraudAlertSerde;


    @Bean
    public KStream<String, FraudAlert> buildPipeline(StreamsBuilder streamsBuilder) {
        // Stream de transações
        KStream<String, Transaction> transactionStream = streamsBuilder
                .stream(TRANSACTIONS_TOPIC, Consumed.with(Serdes.String(), transactionSerde));

        // 1. Detectar transações de alto valor
        KStream<String, FraudAlert> highValueAlerts = transactionStream
                .filter((key, transaction) -> transaction.getAmount().compareTo(new BigDecimal("5000")) > 0)
                .map((key, transaction) -> {
                    FraudAlert alert = new FraudAlert();
                    alert.setCustomerId(transaction.getCustomerId());
                    alert.setAlertType("HIGH_VALUE_TRANSACTION");
                    alert.setDescription("Transação com valor acima de R$ 5.000,00");
                    alert.setDetectionTime(LocalDateTime.now());
                    alert.setSuspiciousTransaction(transaction);
                    alert.setTransactionCount(1);
                    return KeyValue.pair(transaction.getCustomerId(), alert);
                });

        // 2. Detectar múltiplas transações em curto período (janela de 5 minutos)
        KStream<String, FraudAlert> multipleTransactionAlerts = transactionStream
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .filter((windowedCustomerId, count) -> count >= 3)
                .map((windowedCustomerId, count) -> {
                    String customerId = windowedCustomerId.key();
                    FraudAlert alert = new FraudAlert();
                    alert.setCustomerId(customerId);
                    alert.setAlertType("MULTIPLE_TRANSACTIONS");
                    alert.setDescription("3 ou mais transações em um período de 5 minutos");
                    alert.setDetectionTime(LocalDateTime.now());
                    alert.setTransactionCount(count.intValue());
                    return KeyValue.pair(customerId, alert);
                });

        // 3. Unificar alertas e enviar para tópico de saída
        highValueAlerts.merge(multipleTransactionAlerts)
                .to(FRAUD_ALERTS_TOPIC, Produced.with(Serdes.String(), fraudAlertSerde));

        log.info("Stream de processamento de transações financeiras configurado com sucesso!");

        return highValueAlerts;
    }

}
