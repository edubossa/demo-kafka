package com.demo.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * ## Benefícios desta Implementação
 * 1. **Processamento em Tempo Real**: As transações são processadas assim que chegam
 * 2. **Escalabilidade**: Kafka Streams permite escalar horizontalmente para lidar com alto volume de transações
 * 3. **Tolerância a Falhas**: Garante processamento "exactly-once" mesmo em caso de falhas
 * 4. **Processamento Stateful**: Mantém estado das transações por cliente para detectar padrões suspeitos
 * 5. **Baixa Latência**: Alertas são gerados em milissegundos após a detecção
 * 6. **Desacoplamento**: Os sistemas de produção de transações, processamento e consumo de alertas são independentes
 *
 * ## Como Testar
 * 1. Inicie um cluster Kafka local (usando Docker ou diretamente)
 * 2. Execute a aplicação Spring Boot
 * 3. Use o endpoint REST para enviar transações para o sistema
 * 4. Observe os logs para ver os alertas de fraude sendo gerados
 *
 * Este é um caso de uso muito comum e prático para Kafka Streams integrado com Spring Boot em instituições financeiras, fornecendo processamento de dados em tempo real com alta confiabilidade.
 */
@EnableKafkaStreams
@SpringBootApplication
public class DemoKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoKafkaApplication.class, args);
	}

}
