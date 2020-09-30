package logiweb.dto;

import logiweb.entity.enums.CargoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CargoDto {
    private Integer id;
    private String name;
    private Integer weight;
    private CargoStatus status;
    private String cityFrom;
    private String cityTo;
}
