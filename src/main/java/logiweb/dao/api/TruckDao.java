package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.City;
import logiweb.entity.Truck;

import java.util.List;
import java.util.Set;

public interface TruckDao extends GenericDAO<Truck> {
    List<Truck> getAllSorted();

    List<Truck> getAllFreeTrucksInCity(City city);

    List<Truck> getAllFreeTrucksInCities(Set<City> cities, int weight);
}
