package com.calculatorapi.Model;

public enum Operation {
    SUM("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private final String symbol;

    // Constructor to initialize the symbol for the operation
    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}