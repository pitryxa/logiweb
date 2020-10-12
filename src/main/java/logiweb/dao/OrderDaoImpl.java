package logiweb.dao;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl extends GenericDAOImpl<Order> implements OrderDao {
    @Override
    public List<Order> getAllSorted() {
        List<Order> list = entityManager.createQuery("SELECT e FROM Order e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
