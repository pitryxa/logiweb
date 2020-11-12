package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.City;

import java.util.List;

public interface CityDao extends GenericDAO<City> {
    List<City> getAllSortedByName();

    List<City> getAllSortedById();

    City getByName(String name);

    Long countOfCities();
}
