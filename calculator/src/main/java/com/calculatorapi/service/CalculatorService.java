package com.calculatorapi.service;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public BigDecimal getResult(CalculationRequest request) throws ArithmeticException{
        Operation operation = request.getOperation();
        BigDecimal a = request.getOperand1();
        BigDecimal b = request.getOperand2();

        BigDecimal result;
        switch (operation) {
            case SUM:
                result =  a.add(b);
                break;
            case SUBTRACTION:
                result =  a.subtract(b);
                break;
            case MULTIPLICATION:
                result =  a.multiply(b);
                break;
            default:
                //division
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    String errorMessage = String.format("Division by zero attempted: %s/%s", a, b);
                    logger.error(errorMessage);
                    throw new ArithmeticException(errorMessage);
                }
                result =  a.divide(b, RoundingMode.HALF_UP);
                break;
        }
        logger.info("Calculated result: {}", result);
        return result;
    }
}
