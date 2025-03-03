package com.calculatorapi.config;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.request}")
    private String REQUEST_TOPIC;

    @Value("${kafka.topic.reply}")
    private String REPLY_TOPIC;

    @Bean
    public ReplyingKafkaTemplate<String, CalculationRequest, CalculationResponse> replyingKafkaTemplate(
            ProducerFactory<String, CalculationRequest> producerFactory,
            ConcurrentKafkaListenerContainerFactory<String, CalculationResponse> factory) {

        ConcurrentMessageListenerContainer<String, CalculationResponse> replyContainer =
                factory.createContainer(REPLY_TOPIC);

        return new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CalculationResponse> kafkaListenerContainerFactory(
            ConsumerFactory<String, CalculationResponse> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CalculationResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public NewTopic requestTopic() {
        return new NewTopic(REPLY_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic replyTopic() {
        return new NewTopic(REQUEST_TOPIC, 1, (short) 1);
    }
}
