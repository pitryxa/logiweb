package logiweb.dto;

import logiweb.dto.simple.SimpleDriverDto;
import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private SimpleTruckDto truck;
    private List<SimpleDriverDto> drivers;
    private OrderStatus status;
    private List<WaypointDto> waypoints;
}
