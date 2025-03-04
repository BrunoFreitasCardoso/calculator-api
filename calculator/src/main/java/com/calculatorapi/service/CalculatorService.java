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

    public BigDecimal getResult(CalculationRequest request) {
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
                    logger.error("Division by zero attempted: {}/{}", a, b);
                    return null;
                }
                int scale = Math.max(a.scale(), b.scale());
                result =  a.divide(b, scale, RoundingMode.HALF_UP).stripTrailingZeros();
                break;
        }
        logger.info("Calculated result: {}", result);
        return result;
    }
}
