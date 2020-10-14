package logiweb.dao;

import logiweb.dao.api.DriverDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Driver;
import logiweb.entity.enums.DriverStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverDaoImpl extends GenericDAOImpl<Driver> implements DriverDao {
    @Override
    public List<Driver> getAllSorted() {
        List<Driver> list = entityManager.createQuery("FROM Driver e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Driver> getDriversByCityAndWorkTimeAndStatus(String city, double timeWorkForEveryDriver) {
        String query = "FROM Driver e where e.city.name = :city and (176.0 - e.workHours) >= :timeExecOrder " +
                       "and e.status = :status";

        List<Driver> list = entityManager.createQuery(query)
                                         .setParameter("city", city)
                                         .setParameter("timeExecOrder", timeWorkForEveryDriver)
                                         .setParameter("status", DriverStatus.RECREATION)
                                         .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
