package com.calculatorapi.listener;


import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import com.calculatorapi.Model.Operation;

import com.calculatorapi.service.CalculatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalculationRequestListenerTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculationRequestListener calculationRequestListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessCalculation() {
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(5);
        BigDecimal expectedResult = a.add(b);

        String requestId = "1";
        CalculationRequest request = new CalculationRequest(requestId, a, b, Operation.SUM);

        when(calculatorService.getResult(request)).thenAnswer(invocation -> {
            CalculationRequest req = invocation.getArgument(0);
            if (Operation.SUM.equals(req.getOperation())) {
                return req.getOperand1().add(req.getOperand2());  // Perform the actual addition
            }
            throw new IllegalArgumentException("Unexpected operation: " + req.getOperation());
        });

        RecordHeaders headers = new RecordHeaders(new Header[]{
                new RecordHeader("X-Request-ID", requestId.getBytes(StandardCharsets.UTF_8))
        });

        ConsumerRecord<String, CalculationRequest> record = new ConsumerRecord<>(
                "calc-requests", // Topic name
                0,               // Partition
                0,               // Offset
                null,            // Key
                request          // Value (CalculationRequest)
        );

        record.headers().add(headers.lastHeader("X-Request-ID"));

        CalculationResponse response = calculationRequestListener.consume(record);
        assertEquals(requestId, response.getRequestId());
        assertEquals(expectedResult, response.getResult());
    }
}
