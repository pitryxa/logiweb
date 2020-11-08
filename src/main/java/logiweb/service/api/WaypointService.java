package logiweb.service.api;

import logiweb.dto.*;

import java.util.List;

public interface WaypointService {
    List<WaypointDto> getAll();

    void add(WaypointDto waypointDto);

    void delete(WaypointDto waypointDto);

    void edit(WaypointDto waypointDto);

    WaypointDto getById(int id);

    void doneWaypoint(int id, int orderId);

    WaypointDto getCurrentWaypointFromOrder(OrderDto orderDto);
}
