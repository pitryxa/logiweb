package logiweb.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TruckRestDto {
    private Integer allTrucks;
    private Integer freeTrucks;
    private Integer brokenTrucks;

    public TruckRestDto(Integer allTrucks, Integer freeTrucks, Integer brokenTrucks) {
        this.allTrucks = allTrucks;
        this.freeTrucks = freeTrucks;
        this.brokenTrucks = brokenTrucks;
    }
}
