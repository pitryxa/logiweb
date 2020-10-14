package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.City;
import logiweb.entity.Driver;

import java.util.List;

public interface DriverDao extends GenericDAO<Driver> {
    List<Driver> getAllSorted();

    List<Driver> getDriversByCityAndWorkTimeAndStatus(String city, double timeWorkForEveryDriver);
}
