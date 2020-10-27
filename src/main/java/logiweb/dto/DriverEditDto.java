package logiweb.dto;

import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DriverEditDto {
    private Integer id;

    private Long personalNumber;

    private Integer userId;

    private Double workHours;

    private DriverStatus status;

    private Integer truckId;

    private String city;

    private LocalDateTime timeLastChangeStatus;
}
