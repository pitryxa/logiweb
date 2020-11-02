package logiweb.dao;

import logiweb.dao.api.TruckDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.City;
import logiweb.entity.Order;
import logiweb.entity.Truck;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.OrderStatus;
import logiweb.entity.enums.TruckConditionStatus;
import logiweb.entity.enums.TruckWorkStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TruckDaoImpl extends GenericDAOImpl<Truck> implements TruckDao {
    @Override
    public List<Truck> getAllSorted() {
        List<Truck> list =
                entityManager.createQuery("select e from Truck e where e.conditionStatus <> 'DISABLED' order by e.id",
                                          Truck.class).getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Truck> getAllFreeTrucksInCity(City city) {
        List<Truck> list = entityManager.createQuery(
                "select e from Truck e where e.city = :city and e.conditionStatus = :condStatus and e.workStatus = :workStatus",
                Truck.class)
                                        .setParameter("city", city)
                                        .setParameter("condStatus", TruckConditionStatus.OK)
                                        .setParameter("workStatus", TruckWorkStatus.FREE)
                                        .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Truck> getAllFreeTrucksInCities(Set<City> cities, int weight) {
        List<Truck> list = entityManager.createQuery(
                "select e from Truck e where e.city in :cities and e.conditionStatus = :condStatus and e.workStatus = :workStatus and (e.capacity * 1000) >= :weight",
                Truck.class)
                                        .setParameter("cities", cities)
                                        .setParameter("condStatus", TruckConditionStatus.OK)
                                        .setParameter("workStatus", TruckWorkStatus.FREE)
                                        .setParameter("weight", weight)
                                        .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Order getOrderByTruck(Truck truck) {
        List<Order> list = entityManager.createQuery("select o from Order o where o.truck = ?1", Order.class)
                                        .setParameter(1, truck)
                                        .getResultList()
                                        .stream()
                                        .filter(o -> o.getStatus() != OrderStatus.DONE)
                                        .collect(Collectors.toList());

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Integer getCountAllTrucks() {
        return entityManager.createQuery("select count(t) from Truck t where t.conditionStatus <> ?1", Integer.class)
                            .setParameter(1, TruckConditionStatus.DISABLED)
                            .getSingleResult();
    }

    @Override
    public Integer getCountFreeTrucks() {
        return entityManager.createQuery("select count(t) from Truck t where t.workStatus = ?1", Integer.class)
                            .setParameter(1, TruckWorkStatus.FREE)
                            .getSingleResult();
    }

    @Override
    public Integer getCountBrokenTrucks() {
        return entityManager.createQuery("select count(t) from Truck t where t.conditionStatus = ?1", Integer.class)
                            .setParameter(1, TruckConditionStatus.BROKEN)
                            .getSingleResult();
    }
}
