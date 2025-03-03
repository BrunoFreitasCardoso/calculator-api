package com.calculatorapi.controller;

import org.springframework.web.bind.annotation.*;
import com.calculatorapi.service.CalculatorService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private final CalculatorService calculatorService = new CalculatorService();

    @GetMapping("/add")
    public BigDecimal add(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.add(b);
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.subtract(b);
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.multiply(b);
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.equals(BigDecimal.ZERO))
            throw new ArithmeticException();
        return a.divide(b, RoundingMode.HALF_UP);
    }
}
