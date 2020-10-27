package logiweb.dao;

import logiweb.dao.api.CargoDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Cargo;
import logiweb.entity.Order;
import logiweb.entity.WaypointEntity;
import logiweb.entity.enums.CargoStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoDaoImpl extends GenericDAOImpl<Cargo> implements CargoDao {
    @Override
    public List<Cargo> getAllSorted() {
        List<Cargo> list = entityManager.createQuery("select cargo from Cargo cargo order by cargo.id", Cargo.class)
                                        .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Cargo> getPreparedCargo() {
        List<Cargo> list = entityManager.createQuery(
                "select cargo from Cargo cargo where cargo.status = :status order by cargo.id", Cargo.class)
                                        .setParameter("status", CargoStatus.PREPARED)
                                        .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Cargo> getByListId(List<Integer> ids) {
        List<Cargo> list =
                entityManager.createQuery("select cargo from Cargo cargo where cargo.id in :ids", Cargo.class)
                             .setParameter("ids", ids)
                             .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Order getOrderByCargo(Cargo cargo) {
//        List<Order> list = entityManager.createQuery(
//                "select o from Order o where o.id in (select w.order.id from WaypointEntity w where w.cargo.id = ?1)")
//                                        .setParameter(1, cargoId)
//                                        .getResultList();
        List<WaypointEntity> waypointEntityList =
                entityManager.createQuery("select w from WaypointEntity w where w.cargo = :cargo", WaypointEntity.class)
                             .setParameter("cargo", cargo)
                             .getResultList();
//        if (waypointEntityList.isEmpty()) {
//            return null;
//        }

        return waypointEntityList.isEmpty() ? null : waypointEntityList.get(0).getOrder();
//
//        int orderId = waypointEntityList.get(0).get;
//
//        List<Order> orderList = entityManager.createQuery("")
//
//
//        List<Order> list = entityManager.createNativeQuery(
//                "select * from orders where id in (select order_id from waypoints where cargo_id = ?1)", Order.class)
//                     .setParameter(1, cargo)
//                     .getResultList();
//
//        return list.isEmpty() ? null : list.get(0);
    }
}
