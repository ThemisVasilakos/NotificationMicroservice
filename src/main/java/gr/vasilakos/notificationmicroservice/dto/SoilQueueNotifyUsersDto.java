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
public class SoilQueueNotifyUsersDto {

    private List<String> emails;
    private SoilQueueDataDto soilData;
}
