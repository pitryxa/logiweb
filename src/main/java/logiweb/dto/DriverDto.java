package logiweb.dto;

import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverDto {
    private Integer id;

    private UserDto user;

    private Integer workHours;

    private DriverStatus status;

    private SimpleTruckDto truck;

    private String city;
}
