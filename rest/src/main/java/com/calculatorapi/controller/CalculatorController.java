package com.calculatorapi.controller;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import com.calculatorapi.Model.Operation;
import com.calculatorapi.filter.UniqueIdentifierFilter;
import com.calculatorapi.producer.MessageProducer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    private final MessageProducer messageProducer;

    public CalculatorController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/add")
    public ResponseEntity<Object> add(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Summation request: a = {}, b = {}", a, b);

        CalculationRequest calculationRequest = new CalculationRequest(requestId, a, b, Operation.SUM);
        try {
            CalculationResponse response = messageProducer.sendMessage(calculationRequest);
            logger.info("Successfully received sum: {}", response.getResult());
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while communicating with Kafka: {}", e.getMessage());
            throw new KafkaException();
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<Object> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Subtraction request: a = {}, b = {}", a, b);

        CalculationRequest calculationRequest = new CalculationRequest(requestId, a, b, Operation.SUBTRACTION);
        try {
            CalculationResponse response = messageProducer.sendMessage(calculationRequest);
            logger.info("Successfully received difference: {}", response.getResult());
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while communicating with Kafka: {}", e.getMessage());
            throw new KafkaException();
        }
    }

    @GetMapping("/multiply")
    public ResponseEntity<Object> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Multiplication request: a = {}, b = {}", a, b);

        CalculationRequest calculationRequest = new CalculationRequest(requestId, a, b, Operation.MULTIPLICATION);
        try {
            CalculationResponse response = messageProducer.sendMessage(calculationRequest);
            logger.info("Successfully received product: {}", response.getResult());
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while communicating with Kafka: {}", e.getMessage());
            throw new KafkaException();
        }
    }

    @GetMapping("/divide")
    public ResponseEntity<Object> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Division request: a = {}, b = {}", a, b);

        CalculationRequest calculationRequest = new CalculationRequest(requestId, a, b, Operation.DIVISION);
        CalculationResponse response;
        try {
            response = messageProducer.sendMessage(calculationRequest);
            logger.info("Successfully received quotient: {}", response.getResult());
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while communicating with Kafka: {}", e.getMessage());
            throw new KafkaException();
        }
        if (response.getErrorMessage() != null) {
            logger.error(response.getErrorMessage());
            throw new ArithmeticException(response.getErrorMessage());
        }
        logger.info("Successfully received quotient: {}", response.getResult());
        return ResponseEntity.ok(response);
    }
}
