package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Distance;

public interface DistanceDao extends GenericDAO<Distance> {
    Distance get(String city1, String city2);

}
