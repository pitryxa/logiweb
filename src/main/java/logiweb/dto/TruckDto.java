package logiweb.dto;

import logiweb.dto.simple.SimpleDriverDto;
import logiweb.entity.enums.TruckStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TruckDto {
    private Integer id;

    private String regNumber;

    private Integer shiftSize;

    private Integer capacity;

    private TruckStatus status;

    private String city;

    private List<SimpleDriverDto> drivers;
}
