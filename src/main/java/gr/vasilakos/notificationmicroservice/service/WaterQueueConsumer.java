package gr.vasilakos.notificationmicroservice.service;

import gr.vasilakos.notificationmicroservice.dto.WaterQueueDataDto;
import gr.vasilakos.notificationmicroservice.dto.WaterQueueNotifyUsersDto;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaterQueueConsumer {

    private final EmailSender emailSender;

    public WaterQueueConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = {"${rabbitmq.water.queue.name}"})
    public void consume(WaterQueueNotifyUsersDto waterQueueNotifyUsersDto) throws MessagingException {

        System.out.println("Received From Water Queue-> "+ waterQueueNotifyUsersDto.toString());

        List<String> emails = waterQueueNotifyUsersDto.getEmails();
        WaterQueueDataDto waterQueueDataDto = waterQueueNotifyUsersDto.getWaterData();
        String location = waterQueueDataDto.getCoordinates();
        String time = waterQueueDataDto.getTimestamp().substring(0, 23);
        String subject = "Alert: Water Data - Detected: "+location+ " - Time: "+time;

        for (String email : emails) {
            emailSender.sendWaterDataEmail(email, subject, waterQueueDataDto);
        }

    }

}
