package com.calculatorapi.service;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.Operation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testSumOperation() {
        BigDecimal a = new BigDecimal(5);
        BigDecimal b = new BigDecimal(10);
        BigDecimal expectedResult = new BigDecimal(15);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.SUM);
        BigDecimal result = calculatorService.getResult(request);
        assertEquals(expectedResult, result, "Sum operation should return 15.0");
    }

    @Test
    void testSubtractOperation() {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(4);
        BigDecimal expectedResult = new BigDecimal(6);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.SUBTRACTION);
        BigDecimal result = calculatorService.getResult(request);
        assertEquals(expectedResult, result, "Subtract operation should return 6.0");
    }

    @Test
    void testMultiplyOperation() {
        BigDecimal a = new BigDecimal(3);
        BigDecimal b = new BigDecimal(7);
        BigDecimal expectedResult = new BigDecimal(21);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.MULTIPLICATION);
        BigDecimal result = calculatorService.getResult(request);
        assertEquals(expectedResult, result, "Multiply operation should return 21.0");
    }

    @Test
    void testDivideOperation() {
        BigDecimal a = new BigDecimal(20);
        BigDecimal b = new BigDecimal(4);
        BigDecimal expectedResult = new BigDecimal(5);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.DIVISION);
        BigDecimal result = calculatorService.getResult(request);
        assertEquals(expectedResult, result, "Division operation should return 5.0");
    }

    @Test
    void testDivisionByZeroThrowsException() {
        BigDecimal a = new BigDecimal(20);
        BigDecimal b = new BigDecimal(0);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.DIVISION);
        assertThrows(ArithmeticException.class, () -> calculatorService.getResult(request),
                "Division by zero should throw an ArithmeticException");
    }
}
