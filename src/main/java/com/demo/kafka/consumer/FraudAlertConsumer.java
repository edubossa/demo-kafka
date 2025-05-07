package com.demo.kafka.consumer;

import com.demo.kafka.model.FraudAlert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * ### Consumidor para receber alertas de fraude e notificar sistemas externos
 */
@Service
@Slf4j
public class FraudAlertConsumer {

    @KafkaListener(topics = "fraud-alerts", groupId = "fraud-alert-consumer")
    public void consumeFraudAlert(FraudAlert fraudAlert) {
        log.info("ALERTA DE FRAUDE DETECTADO: {}", fraudAlert);

        // Aqui você poderia:
        // 1. Enviar notificação para equipe de segurança
        // 2. Bloquear o cartão automaticamente
        // 3. Enviar SMS para o cliente
        // 4. Registrar o alerta no banco de dados
    }
}
