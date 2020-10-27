package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Cargo;
import logiweb.entity.Order;

import java.util.List;

public interface CargoDao extends GenericDAO<Cargo> {
    List<Cargo> getAllSorted();

    List<Cargo> getPreparedCargo();

    List<Cargo> getByListId(List<Integer> ids);

    Order getOrderByCargo(Cargo cargo);
}
