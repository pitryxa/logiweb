package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.City;
import logiweb.entity.Distance;

import java.util.List;

public interface DistanceDao extends GenericDAO<Distance> {
    Distance get(String city1, String city2);

}
