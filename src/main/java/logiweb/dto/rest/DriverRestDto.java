package logiweb.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverRestDto {
    private Integer allDrivers;
    private Integer freeDrivers;

    public DriverRestDto(Integer all, Integer free) {
        allDrivers = all;
        freeDrivers = free;
    }
}
