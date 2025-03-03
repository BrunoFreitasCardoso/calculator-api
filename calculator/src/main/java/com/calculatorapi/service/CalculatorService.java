package com.calculatorapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorService {
    public BigDecimal add(BigDecimal a, BigDecimal b) { return a.add(b); }
    public BigDecimal subtract(BigDecimal a, BigDecimal b) { return a.subtract(b); }
    public BigDecimal multiply(BigDecimal a, BigDecimal b) { return a.multiply(b); }
    public BigDecimal divide(BigDecimal a, BigDecimal b) { return a.divide(b, RoundingMode.HALF_UP); }
}
