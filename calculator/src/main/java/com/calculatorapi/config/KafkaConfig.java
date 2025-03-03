package com.calculatorapi.config;

import com.calculatorapi.Model.CalculationRequest;
import com.calculatorapi.Model.CalculationResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.request}")
    private String REQUEST_TOPIC;

    @Value("${kafka.topic.reply}")
    private String REPLY_TOPIC;
    ProducerFactory<String, CalculationResponse> producerFactory;

    public KafkaConfig(ProducerFactory<String, CalculationResponse> producerFactory) {
        this.producerFactory = producerFactory;
    }

    @Bean(name = "replyTemplate")
    public KafkaTemplate<String, CalculationResponse> replyTemplate() {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CalculationResponse> kafkaListenerContainerFactory(
            ConsumerFactory<String, CalculationResponse> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CalculationResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(replyTemplate());
        return factory;
    }


    @Bean
    public NewTopic requestTopic() {
        return new NewTopic(REQUEST_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic replyTopic() {
        return new NewTopic(REPLY_TOPIC, 1, (short) 1);
    }
}
