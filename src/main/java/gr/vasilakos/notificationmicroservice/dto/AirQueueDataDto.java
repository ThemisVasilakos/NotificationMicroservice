package gr.vasilakos.notificationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirQueueDataDto {

    private Double airTemperature;
    private Double airHumidity;
    private Double airC02;
    private Double airVOCs;
    private Double airPM25;
    private Double airC0;
    private String timestamp;
    private String coordinates;

}
