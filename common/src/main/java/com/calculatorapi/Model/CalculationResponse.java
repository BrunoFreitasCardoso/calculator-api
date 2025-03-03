package com.calculatorapi.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculationResponse implements Serializable {
    private String requestId;
    private BigDecimal result;
    private String errorMessage;

    // Default constructor (required for Kafka serialization)
    public CalculationResponse() {}

    public CalculationResponse(String requestId, BigDecimal result) {
        this.requestId = requestId;
        this.result = result;
    }

    public CalculationResponse(String requestId, BigDecimal result, String errorMessage) {
        this.requestId = requestId;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public String getRequestId() {

        return requestId;
    }

    public BigDecimal getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format("result= %s, error= %s", result, errorMessage);
    }
}