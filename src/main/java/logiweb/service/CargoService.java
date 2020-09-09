package logiweb.service;

import logiweb.dao.CargoDao;
import logiweb.model.Cargo;

import java.util.List;

public interface CargoService {
    List<Cargo> allCargo();

    void add(Cargo cargo);

    void delete(Cargo cargo);

    void edit(Cargo cargo);

    Cargo getById(int id);
}
