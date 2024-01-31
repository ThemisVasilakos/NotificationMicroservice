package gr.vasilakos.notificationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaterQueueDataDto {

    private Double dissolvedOxygen;
    private Double oxidationReductionPotential;
    private Double pH;
    private Double turbidity;
    private Double totalDissolvedSolids;
    private Double temperature;
    private String timestamp;
    private String coordinates;
}
