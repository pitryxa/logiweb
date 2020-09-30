package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Driver;
import logiweb.entity.Truck;

import java.util.List;

public interface DriverDao extends GenericDAO<Driver> {
    List<Driver> getAllSorted();
}
