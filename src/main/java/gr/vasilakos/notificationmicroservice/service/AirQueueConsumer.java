package gr.vasilakos.notificationmicroservice.service;

import gr.vasilakos.notificationmicroservice.dto.AirQueueDataDto;
import gr.vasilakos.notificationmicroservice.dto.AirQueueNotifyUsersDto;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirQueueConsumer {

    private final EmailSender emailSender;

    public AirQueueConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = {"${rabbitmq.air.queue.name}"})
    public void consume(AirQueueNotifyUsersDto airQueueNotifyUsersDto) throws MessagingException {

        System.out.println("Received From Air Queue-> "+ airQueueNotifyUsersDto.toString());

        List<String> emails = airQueueNotifyUsersDto.getEmails();
        AirQueueDataDto airQueueDataDto = airQueueNotifyUsersDto.getAirData();
        String location = airQueueDataDto.getCoordinates();
        String time = airQueueDataDto.getTimestamp().substring(0, 23);
        String subject = "Alert: Air Data - Detected: "+location+ " - Time: "+time;

        for (String email : emails) {
            emailSender.sendAirDataEmail(email, subject, airQueueDataDto);
        }

    }

}
