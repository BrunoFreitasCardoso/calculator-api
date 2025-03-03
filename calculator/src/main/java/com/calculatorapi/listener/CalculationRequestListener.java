package com.calculatorapi.listener;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import com.calculatorapi.service.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculationRequestListener {
    private static final Logger logger = LoggerFactory.getLogger(CalculationRequestListener.class);
    private final CalculatorService calculatorService;

    public CalculationRequestListener(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "${kafka.topic.request}", groupId = "calc-group")
    @SendTo("${kafka.topic.reply}")
    public CalculationResponse consume(ConsumerRecord<String, CalculationRequest> record) {
        String requestId = null;
        Header header = record.headers().lastHeader("X-Request-ID");
        if (header != null) {
            requestId = new String(header.value());
        }
        CalculationRequest request = record.value();
        logger.info("Received calculation request from Kafka: {} (Request ID: {})", request, requestId);

        try{
            BigDecimal result = calculatorService.getResult(request);
            CalculationResponse response = new CalculationResponse(requestId, result);
            logger.info("Sending calculation response to Kafka: {}", response);
            return response;
        }catch (ArithmeticException e){
            logger.error("Arithmetic error for request: {}. Error: {}", request, e.getMessage());
            CalculationResponse errorResponse = new CalculationResponse(requestId, null, e.getMessage());
            logger.info("Sending error response to Kafka: {}", errorResponse);
            return errorResponse;
        }
    }
}
