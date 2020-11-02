package logiweb.dao;

import logiweb.dao.api.OrderDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.Truck;
import logiweb.entity.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderDaoImpl extends GenericDAOImpl<Order> implements OrderDao {
    @Override
    public List<Order> getAllSorted() {
        List<Order> list = entityManager.createQuery("SELECT e FROM Order e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Order getOrderByTruckId(Integer truckId) {
        List<Order> list =
                entityManager.createNativeQuery("select * from orders where truck_id = " + truckId, Order.class)
                             .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void setStatus(int orderId, OrderStatus status) {
        String query = "update Order set status = :status where id = :id";
        entityManager.createQuery(query).setParameter("status", status).setParameter("id", orderId).executeUpdate();
    }

    @Override
    public List<Driver> getDrivers(int orderId) {
        String query =
                "select driver from Driver driver where driver.truck = (select order.truck from Order order where order.id = :id)";
        return entityManager.createQuery(query, Driver.class).setParameter("id", orderId).getResultList();
    }

    @Override
    public Truck getTruck(int orderId) {
        String query = "select o.truck from Order o where o.id = :id";

        List<Truck> list = entityManager.createQuery(query, Truck.class).setParameter("id", orderId).getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void addToOrderDriversTable(int orderId, int driverId) {
        String query = "insert into order_drivers (order_id, driver_id) values (:order, :driver)";

        entityManager.createNativeQuery(query)
                     .setParameter("order", orderId)
                     .setParameter("driver", driverId)
                     .executeUpdate();
    }

    @Override
    public List<Order> getTenLast() {
        List<Order> list = entityManager.createQuery("SELECT e FROM Order e ORDER BY e.id desc", Order.class)
                                        .setMaxResults(10)
                                        .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

}
