package logiweb.dao;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.TruckDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Driver;
import logiweb.entity.Truck;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverDaoImpl extends GenericDAOImpl<Driver> implements DriverDao {
    @Override
    public List<Driver> getAllSorted() {
        List<Driver> list = entityManager.createQuery("SELECT e FROM Driver e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }


}
