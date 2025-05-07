package com.demo.kafka.config;

import com.demo.kafka.model.FraudAlert;
import com.demo.kafka.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

/**
 * ### Configuração do Serializador/Deserializador para JSON
 */
@Configuration
public class SerdeConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public JsonSerde<Transaction> transactionSerde(ObjectMapper mapper) {
        JsonSerde<Transaction> serde = new JsonSerde<>(Transaction.class, mapper);
        serde.deserializer().addTrustedPackages("com.demo.kafka.model");
        return serde;
    }

    @Bean
    public JsonSerde<FraudAlert> fraudAlertSerde(ObjectMapper mapper) {
        JsonSerde<FraudAlert> serde = new JsonSerde<>(FraudAlert.class, mapper);
        serde.deserializer().addTrustedPackages("com.demo.kafka.model");
        return serde;
    }

}