package logiweb.dto;

import logiweb.entity.enums.CargoStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CargoDto {
    private Integer id;
    private String name;
    private Integer weight;
    private CargoStatus status;
    private String cityFrom;
    private String cityTo;
    private Integer orderId;

    @Override
    public String toString() {
        return "CargoDto{" + "id=" + id + ", name='" + name + '\'' + ", weight=" + weight + ", status=" + status +
               ", cityFrom='" + cityFrom + '\'' + ", cityTo='" + cityTo + '\'' + '}';
    }
}
