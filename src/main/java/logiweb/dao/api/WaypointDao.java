package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Cargo;
import logiweb.entity.WaypointEntity;

import java.util.List;

public interface WaypointDao extends GenericDAO<WaypointEntity> {
    void doneWaypoint(int id);

    boolean isAllWaypointsDone(int orderId);

    boolean isUnloadWaypoint(int id);

    WaypointEntity getById(int id);

    Cargo getCargoByWaypointId(int id);

    List<WaypointEntity> getAllByOrderId(Integer orderId);
}
