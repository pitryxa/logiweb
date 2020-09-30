package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.Cargo;

import java.util.List;

public interface CargoDao extends GenericDAO<Cargo> {
    List<Cargo> getAllSorted();
}
