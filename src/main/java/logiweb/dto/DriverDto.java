package logiweb.dto;

import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DriverDto implements Serializable {
    private Integer id;

    private Long personalNumber;

    private UserDto user;

    private Double workHours;

    private DriverStatus status;

    private SimpleTruckDto truck;

    private String city;

    private LocalDateTime timeLastChangeStatus;

    private Integer orderId;
}
