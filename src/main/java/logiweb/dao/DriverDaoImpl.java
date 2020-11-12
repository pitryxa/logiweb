package logiweb.dao;

import logiweb.dao.api.DriverDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverDaoImpl extends GenericDAOImpl<Driver> implements DriverDao {
    @Override
    public List<Driver> getAllSorted() {
        List<Driver> list =
                entityManager.createQuery("select e from Driver e ORDER BY e.id desc", Driver.class).getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Driver> getAllNotDisabled() {
        List<Driver> list =
                entityManager.createQuery("select e from Driver e where e.status <> :disabled ORDER BY e.id desc",
                                          Driver.class).setParameter("disabled", DriverStatus.DISABLED).getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Driver> getDriversByCityAndWorkTimeAndStatus(String city, double timeWorkForEveryDriver) {
        String query = "select e from Driver e where e.city.name = :city and (176.0 - e.workHours) >= :timeExecOrder " +
                       "and e.status = :status";

        List<Driver> list = entityManager.createQuery(query, Driver.class)
                                         .setParameter("city", city)
                                         .setParameter("timeExecOrder", timeWorkForEveryDriver)
                                         .setParameter("status", DriverStatus.RECREATION)
                                         .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Driver> getDriversByCityAndStatus(String city) {
        String query = "select e from Driver e where e.city.name = :city and e.status = :status";

        List<Driver> list = entityManager.createQuery(query, Driver.class)
                                         .setParameter("city", city)
                                         .setParameter("status", DriverStatus.RECREATION)
                                         .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Driver getByUserId(int userId) {
        List<Driver> list =
                entityManager.createQuery("select d from Driver d where d.user.id = " + userId, Driver.class)
                             .getResultList();
        return list.isEmpty() ? new Driver() : list.get(0);
    }

    @Override
    public List<Driver> getByOrderId(Integer orderId) {
        List<Driver> list = entityManager.createNativeQuery(
                "select * from driver where id in (select driver_id from order_drivers where order_id = ?1)",
                Driver.class).setParameter(1, orderId).getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public void disableByUserId(Integer userId) {
        entityManager.createQuery("update Driver set status = :status where user.id = :userId")
                     .setParameter("status", DriverStatus.DISABLED)
                     .setParameter("userId", userId)
                     .executeUpdate();
    }

    @Override
    public Order getOrderByDriver(Driver driver) {
        List<Order> list = entityManager.createQuery("select o from Order o where o.truck = ?1 and o.status <> 'DONE'",
                                                     Order.class).setParameter(1, driver.getTruck()).getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Integer getCountFreeDrivers() {
        Long result = (Long) entityManager.createQuery("select count(d) from Driver d where d.status = ?1")
                                          .setParameter(1, DriverStatus.RECREATION)
                                          .getSingleResult();
        return result.intValue();
    }

    @Override
    public boolean isUserAssignToOrder(Integer userId) {
        List<Driver> driverList =
                entityManager.createQuery("select d from Driver d where d.user.id = " + userId, Driver.class)
                             .getResultList();

        if (driverList.isEmpty()) {
            return false;
        }

        Driver driver = driverList.get(0);

        if (driver.getStatus() == DriverStatus.DISABLED) {
            return false;
        }

        List<Integer> orderIds =
                entityManager.createNativeQuery("select order_id from order_drivers where driver_id = ?1")
                             .setParameter(1, driver.getId())
                             .getResultList();

        List<Order> orderList =
                entityManager.createQuery("select o from Order o where o.status <> ?1 and o.id in ?2", Order.class)
                             .setParameter(1, OrderStatus.DONE)
                             .setParameter(2, orderIds)
                             .getResultList();

        return !orderList.isEmpty();
    }

    @Override
    public Driver getByPersonalNumber(Long personalNumber) {
        Driver driver;

        try {
            driver = entityManager.createQuery("select d from Driver d where d.personalNumber = ?1", Driver.class)
                                  .setParameter(1, personalNumber)
                                  .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return driver;
    }

    @Override
    public Integer getCountAllDrivers() {
        Long result = entityManager.createQuery("select count(d) from Driver d where d.status <> ?1", Long.class)
                                   .setParameter(1, DriverStatus.DISABLED)
                                   .getSingleResult();

        return result.intValue();
    }


}
