package gr.vasilakos.notificationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirQueueNotifyUsersDto {

    private List<String> emails;
    private AirQueueDataDto airData;
}
