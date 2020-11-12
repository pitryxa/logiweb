package logiweb.dto;

import logiweb.entity.enums.CargoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CargoDto implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CargoDto cargoDto = (CargoDto) o;
        return id.equals(cargoDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
