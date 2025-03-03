package com.calculatorapi.controller;

import com.calculatorapi.filter.UniqueIdentifierFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.calculatorapi.service.CalculatorService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @GetMapping("/add")
    public BigDecimal add(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Summation request: a = {}, b = {}", a, b);
        return a.add(b);
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Subtraction request: a = {}, b = {}", a, b);
        return a.subtract(b);
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Multiplication request: a = {}, b = {}", a, b);
        return a.multiply(b);
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletRequest request) {
        String requestId = (String) request.getAttribute(UniqueIdentifierFilter.REQUEST_ID_HEADER);
        logger.info("Division request: a = {}, b = {}", a, b);
        if (b.equals(BigDecimal.ZERO))
            throw new ArithmeticException();
        return a.divide(b, RoundingMode.HALF_UP);
    }
}
