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
}
