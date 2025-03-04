package com.calculatorapi.producer;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@Service
public class MessageProducer {
    private final ReplyingKafkaTemplate<String, CalculationRequest, CalculationResponse> replyingKafkaTemplate;

    @Value("${kafka.topic.request}")
    private String REQUEST_TOPIC;

    @Value("${kafka.topic.reply}")
    private String REPLY_TOPIC;

    public MessageProducer(ReplyingKafkaTemplate<String, CalculationRequest, CalculationResponse> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public CalculationResponse sendAndReceive(CalculationRequest request) throws ExecutionException, InterruptedException {
        ProducerRecord<String, CalculationRequest> record = new ProducerRecord<>(REQUEST_TOPIC, request);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, REPLY_TOPIC.getBytes()));
        record.headers().add(new RecordHeader("X-Request-ID", request.getRequestId().getBytes(StandardCharsets.UTF_8)));

        RequestReplyFuture<String, CalculationRequest, CalculationResponse> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, CalculationResponse> responseRecord = future.get();
        return responseRecord.value();
    }
}
