package logiweb.dto;

import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WaypointDto {
    private Integer id;
    private Integer orderId;
    private String city;
    private CargoDto cargo;
    private OperationTypeOnWaypoint operation;
    private Integer sequentialNumber;
}
