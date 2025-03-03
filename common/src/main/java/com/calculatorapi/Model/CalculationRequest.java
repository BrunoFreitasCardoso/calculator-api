package com.calculatorapi.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculationRequest implements Serializable  {
    private String requestId;
    private BigDecimal operand1;
    private BigDecimal operand2;
    private Operation operation;

    // Default constructor (required for deserialization)
    public CalculationRequest() {}

    public CalculationRequest(String requestId, BigDecimal operand1, BigDecimal operand2, Operation operation) {
        this.requestId = requestId;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    public String getRequestId() {
        return requestId;
    }

    public BigDecimal getOperand1() {
        return operand1;
    }

    public BigDecimal getOperand2() {
        return operand2;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return String.format("request: %s %s %s", operand1, operation, operand2);
    }
}