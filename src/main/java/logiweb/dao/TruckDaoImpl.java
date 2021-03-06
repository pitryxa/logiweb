package logiweb.dao;

import logiweb.dao.api.TruckDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.City;
import logiweb.entity.Order;
import logiweb.entity.Truck;
import logiweb.entity.enums.OrderStatus;
import logiweb.entity.enums.TruckConditionStatus;
import logiweb.entity.enums.TruckWorkStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TruckDaoImpl extends GenericDAOImpl<Truck> implements TruckDao {
    @Override
    public List<Truck> getAllSorted() {
        List<Truck> list =
                entityManager.createQuery("select e from Truck e where e.conditionStatus <> 'DISABLED' order by e.id desc",
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
        Long result = (Long) entityManager.createQuery("select count(t) from Truck t where t.conditionStatus <> ?1")
                                          .setParameter(1, TruckConditionStatus.DISABLED)
                                          .getSingleResult();
        return result.intValue();
    }

    @Override
    public Integer getCountFreeTrucks() {
        Long result = (Long) entityManager.createQuery(
                "select count(t) from Truck t where t.workStatus = ?1 and t.conditionStatus = ?2")
                                          .setParameter(1, TruckWorkStatus.FREE)
                                          .setParameter(2, TruckConditionStatus.OK)
                                          .getSingleResult();
        return result.intValue();
    }

    @Override
    public Integer getCountBrokenTrucks() {
        Long result = (Long) entityManager.createQuery("select count(t) from Truck t where t.conditionStatus = ?1")
                                          .setParameter(1, TruckConditionStatus.BROKEN)
                                          .getSingleResult();
        return result.intValue();
    }

    @Override
    public Truck getByRegNumber(String regNumber) {
        Truck truck;

        try {
            truck = entityManager.createQuery("select t from Truck t where t.regNumber = ?1", Truck.class)
                                 .setParameter(1, regNumber)
                                 .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return truck;
    }

    @Override
    public Truck getFreeTruckById(Integer id) {
        Truck truck;
        try {
            truck = entityManager.createQuery("select t from Truck t where t.id = ?1 and t.workStatus = 'FREE' and t.conditionStatus = 'OK'", Truck.class)
                                 .setParameter(1, id)
                                 .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return truck;
    }
}
