package logiweb.dao;

import logiweb.dao.api.TruckDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Truck;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TruckDaoImpl extends GenericDAOImpl<Truck> implements TruckDao {
    @Override
    public List<Truck> getAllSorted() {
        List<Truck> list = entityManager.createQuery("SELECT e FROM Truck e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }


}
