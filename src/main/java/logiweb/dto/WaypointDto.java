package logiweb.dto;

import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.entity.enums.WaypointStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private WaypointStatus status;

    @Override
    public String toString() {
        return "WaypointDto{" + "id=" + id + ", orderId=" + orderId + ", city='" + city + '\'' + ", operation=" +
               operation + ", sequentialNumber=" + sequentialNumber + ", status=" + status + '}';
    }
}
