package logiweb.dao;

import logiweb.dao.api.WaypointDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Cargo;
import logiweb.entity.WaypointEntity;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.entity.enums.WaypointStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WaypointDaoImpl extends GenericDAOImpl<WaypointEntity> implements WaypointDao {


    @Override
    public void doneWaypoint(int id) {
        String query = "update WaypointEntity set status = 'DONE' where id = :id";
        entityManager.createQuery(query).setParameter("id", id).executeUpdate();
    }

    @Override
    public boolean isAllWaypointsDone(int orderId) {
        String query = "select count(e) from WaypointEntity e where e.order.id = :orderId and e.status = :undone";

        return (Long) entityManager.createQuery(query)
                                   .setParameter("orderId", orderId)
                                   .setParameter("undone", WaypointStatus.UNDONE)
                                   .getSingleResult() == 0L;
    }

    @Override
    public boolean isUnloadWaypoint(int id) {
        return getById(id).getOperation() == OperationTypeOnWaypoint.UNLOAD;
    }

    @Override
    public WaypointEntity getById(int id) {
        return entityManager.find(WaypointEntity.class, id);
    }

    @Override
    public Cargo getCargoByWaypointId(int id) {
        List<Cargo> list = entityManager.createQuery("select w.cargo from WaypointEntity w where w.id = ?1")
                                                 .setParameter(1, id)
                                                 .getResultList();
        return list.isEmpty() ? new Cargo() : list.get(0);
    }
}
