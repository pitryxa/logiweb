package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDAO<Order> {
    List<Order> getAllSorted();
}
