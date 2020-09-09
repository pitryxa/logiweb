package logiweb.dao;

import logiweb.model.Cargo;

import java.util.List;

public interface CargoDao {
    List<Cargo> allCargo();
    void add(Cargo cargo);
    void delete(Cargo cargo);
    void edit(Cargo cargo);
    Cargo getById(int id);
}
