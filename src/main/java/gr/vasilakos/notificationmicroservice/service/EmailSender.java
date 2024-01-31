package gr.vasilakos.notificationmicroservice.service;

import gr.vasilakos.notificationmicroservice.dto.AirQueueDataDto;
import gr.vasilakos.notificationmicroservice.dto.SoilQueueDataDto;
import gr.vasilakos.notificationmicroservice.dto.WaterQueueDataDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("th3m.them@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    public void sendAirDataEmail(String toEmail, String subject, AirQueueDataDto airData) throws MessagingException {

        String body = generateHtmlTableForAirData(airData);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {
            helper.setFrom("th3m.them@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body, true);
            helper.setSubject(subject);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public void sendWaterDataEmail(String email, String subject, WaterQueueDataDto waterQueueDataDto) throws MessagingException {

        String body = generateHtmlTableForWaterData(waterQueueDataDto);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {
            helper.setFrom("th3m.them@gmail.com");
            helper.setTo(email);
            helper.setText(body, true);
            helper.setSubject(subject);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public void sendSoilDataEmail(String email, String subject, SoilQueueDataDto soilQueueDataDto) throws MessagingException {

        String body = generateHtmlTableForSoilData(soilQueueDataDto);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {
            helper.setFrom("th3m.them@gmail.com");
            helper.setTo(email);
            helper.setText(body, true);
            helper.setSubject(subject);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    private String generateHtmlTableForSoilData(SoilQueueDataDto soilQueueDataDto) {
        StringBuilder htmlBody = new StringBuilder("<html><head><style>"
                + "table { width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 20px; }"
                + "th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }"
                + "th { background-color: #f8f8f8; }"
                + "tr:nth-child(even) { background-color: #f2f2f2; }"
                + "td:nth-child(2) { padding-right: 5px; color: #007BFF; }" // Color for the "Value" column
                + "td:nth-child(3) { color: #28A745; }" // Color for the "Unit" column
                + "</style></head><body><h2>Soil Quality Data</h2>");

        // Create an HTML table with the soil data and measurement units
        htmlBody.append("<table><tr><th>Parameter</th><th>Value</th><th>Unit</th></tr>");
        htmlBody.append("<tr><td>Temperature</td><td>").append(soilQueueDataDto.getSoilTemperature()).append("</td><td>°C</td></tr>");
        htmlBody.append("<tr><td>Moisture</td><td>").append(soilQueueDataDto.getSoilMoisture()).append("</td><td>cbar</td></tr>");
        htmlBody.append("<tr><td>Electronic Conductivity</td><td>").append(soilQueueDataDto.getElectronicConductivity()).append("</td><td>mS/m</td></tr>");
        htmlBody.append("<tr><td>Volumetric Water Content</td><td>").append(soilQueueDataDto.getVolumetricWaterContent()).append("</td><td>dS/m</td></tr>");
        htmlBody.append("<tr><td>Salinity</td><td>").append(soilQueueDataDto.getSalinity()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Total Suspended Solids</td><td>").append(soilQueueDataDto.getTotalSuspendedSolids()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Flow/Volume</td><td>").append(soilQueueDataDto.getFlowVolume()).append("</td><td>m3</td></tr>");
        htmlBody.append("<tr><td>Nitrate</td><td>").append(soilQueueDataDto.getNitrate()).append("</td><td>mg/L</td></tr>");
        htmlBody.append("<tr><td>Timestamp</td><td>").append(soilQueueDataDto.getTimestamp()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Location</td><td>").append(soilQueueDataDto.getCoordinates()).append("</td><td></td></tr>");
        htmlBody.append("</table></body></html>");

        return htmlBody.toString();
    }

    private String generateHtmlTableForWaterData(WaterQueueDataDto waterQueueDataDto) {
        StringBuilder htmlBody = new StringBuilder("<html><head><style>"
                + "table { width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 20px; }"
                + "th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }"
                + "th { background-color: #f8f8f8; }"
                + "tr:nth-child(even) { background-color: #f2f2f2; }"
                + "td:nth-child(2) { padding-right: 5px; color: #007BFF; }" // Color for the "Value" column
                + "td:nth-child(3) { color: #28A745; }" // Color for the "Unit" column
                + "</style></head><body><h2>Water Quality Data</h2>");

        // Create an HTML table with the water data and measurement units
        htmlBody.append("<table><tr><th>Parameter</th><th>Value</th><th>Unit</th></tr>");
        htmlBody.append("<tr><td>Dissolved Oxygen</td><td>").append(waterQueueDataDto.getDissolvedOxygen()).append("</td><td>mg/L</td></tr>");
        htmlBody.append("<tr><td>Oxidation-Reduction Potential</td><td>").append(waterQueueDataDto.getOxidationReductionPotential()).append("</td><td>mV</td></tr>");
        htmlBody.append("<tr><td>pH</td><td>").append(waterQueueDataDto.getPH()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Turbidity</td><td>").append(waterQueueDataDto.getTurbidity()).append("</td><td>NTU</td></tr>");
        htmlBody.append("<tr><td>Total Dissolved Solids</td><td>").append(waterQueueDataDto.getTotalDissolvedSolids()).append("</td><td>ppm</td></tr>");
        htmlBody.append("<tr><td>Temperature</td><td>").append(waterQueueDataDto.getTemperature()).append("</td><td>°C</td></tr>");
        htmlBody.append("<tr><td>Timestamp</td><td>").append(waterQueueDataDto.getTimestamp()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Location</td><td>").append(waterQueueDataDto.getCoordinates()).append("</td><td></td></tr>");
        htmlBody.append("</table></body></html>");

        return htmlBody.toString();
    }

    private String generateHtmlTableForAirData(AirQueueDataDto airData) {
        StringBuilder htmlBody = new StringBuilder("<html><head><style>"
                + "table { width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 20px; }"
                + "th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }"
                + "th { background-color: #f8f8f8; }"
                + "tr:nth-child(even) { background-color: #f2f2f2; }"
                + "td:nth-child(2) { padding-right: 5px; color: #007BFF; }" // Color for the "Value" column
                + "td:nth-child(3) { color: #28A745; }" // Color for the "Unit" column
                + "</style></head><body><h2>Air Quality Data</h2>");

        // Create an HTML table with the sensor data and measurement units
        htmlBody.append("<table><tr><th>Parameter</th><th>Value</th><th>Unit</th></tr>");
        htmlBody.append("<tr><td>Temperature</td><td>").append(airData.getAirTemperature()).append("</td><td>°C</td></tr>");
        htmlBody.append("<tr><td>Humidity</td><td>").append(airData.getAirHumidity()).append("</td><td>%</td></tr>");
        htmlBody.append("<tr><td>CO2</td><td>").append(airData.getAirC02()).append("</td><td>ppm</td></tr>");
        htmlBody.append("<tr><td>VOCs</td><td>").append(airData.getAirVOCs()).append("</td><td>ppb</td></tr>");
        htmlBody.append("<tr><td>PM2.5</td><td>").append(airData.getAirPM25()).append("</td><td>µg/m³</td></tr>");
        htmlBody.append("<tr><td>CO</td><td>").append(airData.getAirC0()).append("</td><td>ppm</td></tr>");
        htmlBody.append("<tr><td>Timestamp</td><td>").append(airData.getTimestamp()).append("</td><td></td></tr>");
        htmlBody.append("<tr><td>Location</td><td>").append(airData.getCoordinates()).append("</td><td></td></tr>");
        htmlBody.append("</table></body></html>");

        return htmlBody.toString();
    }


    
}
