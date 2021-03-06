package logiweb.dto;

import logiweb.dto.simple.SimpleDriverDto;
import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer distance;

    @Override
    public String toString() {
        return "OrderDto{" + "id=" + id + ", truck=" + truck + ", drivers=" + drivers + ", status=" + status + '}';
    }
}
