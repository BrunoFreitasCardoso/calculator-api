package com.calculatorapi.Model;

import java.io.Serializable;
import java.math.BigDecimal;


public class CalculationResponse implements Serializable {
    private BigDecimal result;

    // Default constructor (required for Kafka serialization)
    public CalculationResponse() {}

    public CalculationResponse(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("result= %s", result);
    }
}