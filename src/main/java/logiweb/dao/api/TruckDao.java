package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Truck;

import java.util.List;

public interface TruckDao extends GenericDAO<Truck> {
    List<Truck> getAllSorted();
}
