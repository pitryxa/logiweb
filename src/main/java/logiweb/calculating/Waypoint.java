package logiweb.calculating;

import logiweb.dto.CargoDto;
import logiweb.dto.CityDto;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"city"})
public class Waypoint {
    private CityDto city;
    private Map<CargoDto, OperationTypeOnWaypoint> cargoes = new HashMap<>();
    private Integer sumWeight = 0;
    private Integer distanceFromPrevWaypoint = 0;

    public Waypoint(CityDto city) {
        setCity(city);
    }

    public Waypoint(Waypoint waypoint) {
        setCity(waypoint.getCity());
        setCargoes(new HashMap<>(waypoint.getCargoes()));
        setSumWeight(waypoint.getSumWeight());
        setDistanceFromPrevWaypoint(waypoint.getDistanceFromPrevWaypoint());
    }

    public Waypoint(Waypoint waypoint, Integer distanceFromPrevWaypoint, Integer sumWeight) {
        setCity(waypoint.getCity());
        setCargoes(new HashMap<>(waypoint.getCargoes()));
        setSumWeight(sumWeight);
        setDistanceFromPrevWaypoint(distanceFromPrevWaypoint);

    }

    public void addCargo(CargoDto cargoDto, OperationTypeOnWaypoint operation) {
        cargoes.put(cargoDto, operation);
    }


}
