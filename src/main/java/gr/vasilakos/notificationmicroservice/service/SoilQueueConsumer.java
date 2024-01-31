package gr.vasilakos.notificationmicroservice.service;

import gr.vasilakos.notificationmicroservice.dto.SoilQueueDataDto;
import gr.vasilakos.notificationmicroservice.dto.SoilQueueNotifyUsersDto;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilQueueConsumer {

    private final EmailSender emailSender;

    public SoilQueueConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = {"${rabbitmq.soil.queue.name}"})
    public void consume(SoilQueueNotifyUsersDto soilQueueNotifyUsersDto) throws MessagingException {

        System.out.println("Received From Soil Queue-> "+ soilQueueNotifyUsersDto.toString());

        List<String> emails = soilQueueNotifyUsersDto.getEmails();
        SoilQueueDataDto soilQueueDataDto = soilQueueNotifyUsersDto.getSoilData();
        String location = soilQueueDataDto.getCoordinates();
        String time = soilQueueDataDto.getTimestamp().substring(0, 23);
        String subject = "Alert: Soil Data - Detected: "+location+ " - Time: "+time;

        for (String email : emails) {
            emailSender.sendSoilDataEmail(email, subject, soilQueueDataDto);
        }

    }

}
