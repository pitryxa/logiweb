package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Driver;
import logiweb.entity.Order;

import java.util.List;

public interface DriverDao extends GenericDAO<Driver> {
    List<Driver> getAllSorted();

    List<Driver> getAllNotDisabled();

    List<Driver> getDriversByCityAndWorkTimeAndStatus(String city, double timeWorkForEveryDriver);

    List<Driver> getDriversByCityAndStatus(String city);

    Driver getByUserId(int userId);

    List<Driver> getByOrderId(Integer orderId);

    void disableByUserId(Integer userId);

    Order getOrderByDriver(Driver driver);

    Integer getCountAllDrivers();

    Integer getCountFreeDrivers();

    boolean isUserAssignToOrder(Integer userId);

    Driver getByPersonalNumber(Long personalNumber);
}
