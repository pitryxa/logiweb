package logiweb.calculating;

import logiweb.dto.CargoDto;
import logiweb.dto.CityDto;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"city"})
public class Waypoint {
    private CityDto city;
    private Map<CargoDto, OperationTypeOnWaypoint> cargoes = new LinkedHashMap<>();
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

    public void clearCargoes() {
        cargoes.clear();
    }

    public void sortCargoes() {
        cargoes = cargoes.entrySet()
                         .stream()
                         .sorted(Map.Entry.comparingByValue())
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                   (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


}
