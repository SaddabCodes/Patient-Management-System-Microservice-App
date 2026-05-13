package com.sadcodes.patientservice.kafka;

import com.sadcodes.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    @Value("${spring.kafka.topic.patient}")
    private String patientTopic;

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            CompletableFuture<SendResult<String, byte[]>> future =
                    kafkaTemplate.send(patientTopic, patient.getId().toString(), event.toByteArray());

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to send patient event to topic {}", patientTopic, ex);
                    return;
                }

                log.info(
                        "Sent patient event to topic {} partition {} offset {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            });
        } catch (Exception e) {
            log.error("Error scheduling PatientCreated event send for topic {}", patientTopic, e);
        }
    }
}
