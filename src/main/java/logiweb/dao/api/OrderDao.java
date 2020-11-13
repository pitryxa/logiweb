package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.Truck;
import logiweb.entity.enums.OrderStatus;

import java.util.List;

public interface OrderDao extends GenericDAO<Order> {
    List<Order> getAllSorted();

    Order getOrderByTruckId(Integer truckId);

    void setStatus(int orderId, OrderStatus status);

    List<Driver> getDrivers(int orderId);

    Truck getTruck(int orderId);

    void addToOrderDriversTable(int orderId, int driverId);

    List<Order> getTenLast();

    void deleteById(Integer id);
}
