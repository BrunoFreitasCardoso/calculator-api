package com.calculatorapi;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import com.calculatorapi.Model.Operation;
import com.calculatorapi.controller.CalculatorController;
import com.calculatorapi.producer.MessageProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageProducer messageProducer;

    private CalculatorController calculatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        calculatorController = new CalculatorController(messageProducer);
        mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();
    }

    @Test
    void testSumEndpoint() throws Exception {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(3.0);
        BigDecimal expectedResult = a.add(b);

        when(messageProducer.sendAndReceive(any(CalculationRequest.class)))
                .thenAnswer(invocation -> {
                    CalculationRequest request = invocation.getArgument(0);
                    if (Operation.SUM.equals(request.getOperation())) {
                        return new CalculationResponse(expectedResult);
                    }
                    throw new IllegalArgumentException("Unexpected operation");
                });

        CalculationResponse expectedResponse = new CalculationResponse(expectedResult);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/add")
                        .param("a", String.valueOf(a))
                        .param("b", String.valueOf(b)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void testSubtractEndpoint() throws Exception {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(3.0);
        BigDecimal expectedResult = a.subtract(b);

        when(messageProducer.sendAndReceive(any(CalculationRequest.class)))
                .thenAnswer(invocation -> {
                    CalculationRequest request = invocation.getArgument(0);
                    if (Operation.SUBTRACTION.equals(request.getOperation())) {
                        return new CalculationResponse(expectedResult);
                    }
                    throw new IllegalArgumentException("Unexpected operation");
                });

        CalculationResponse expectedResponse = new CalculationResponse(expectedResult);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/subtract")
                        .param("a", String.valueOf(a))
                        .param("b", String.valueOf(b)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void testMultiplyEndpoint() throws Exception {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(3.0);
        BigDecimal expectedResult = a.multiply(b);

        when(messageProducer.sendAndReceive(any(CalculationRequest.class)))
                .thenAnswer(invocation -> {
                    CalculationRequest request = invocation.getArgument(0);
                    if (Operation.MULTIPLICATION.equals(request.getOperation())) {
                        return new CalculationResponse(expectedResult);
                    }
                    throw new IllegalArgumentException("Unexpected operation");
                });

        CalculationResponse expectedResponse = new CalculationResponse(expectedResult);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/multiply")
                        .param("a", String.valueOf(a))
                        .param("b", String.valueOf(b)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void testDivideEndpoint() throws Exception {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(5);
        BigDecimal expectedResult = a.divide(b, 10, RoundingMode.HALF_DOWN);

        when(messageProducer.sendAndReceive(any(CalculationRequest.class)))
                .thenAnswer(invocation -> {
                    CalculationRequest request = invocation.getArgument(0);
                    if (Operation.DIVISION.equals(request.getOperation())) {
                        return new CalculationResponse(expectedResult);
                    }
                    throw new IllegalArgumentException("Unexpected operation");
                });

        CalculationResponse expectedResponse = new CalculationResponse(expectedResult);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/divide")
                        .param("a", String.valueOf(a))
                        .param("b", String.valueOf(b)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }
}
