package logiweb.service.api;

import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
import logiweb.dto.*;
import logiweb.entity.Distance;

import java.util.List;

public interface WaypointService {
    List<WaypointDto> getAll();

    void add(WaypointDto waypointDto);

    void delete(WaypointDto waypointDto);

    void edit(WaypointDto waypointDto);

    WaypointDto getById(int id);

    List<Waypoint> getUnorderedWaypointsFromCargoes(List<CargoDto> cargoes, List<CityDto> allCities);

    Route minRouteBetweenTwoCities(List<CityDto> allCities, List<DistanceDto> allDistances, CityDto cityFrom, CityDto cityTo);

    Route minRouteByCargoes(List<CargoDto> cargoes, TruckDto truck);

}
