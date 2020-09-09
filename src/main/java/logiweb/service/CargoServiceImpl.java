package logiweb.service;

import logiweb.dao.CargoDao;
import logiweb.dao.CargoDaoImpl;
import logiweb.model.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService{
    private CargoDao cargoDao;

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    @Transactional
    public List<Cargo> allCargo() {
        return cargoDao.allCargo();
    }

    @Override
    @Transactional
    public void add(Cargo cargo) {
        cargoDao.add(cargo);
    }

    @Override
    @Transactional
    public void delete(Cargo cargo) {
        cargoDao.delete(cargo);
    }

    @Override
    @Transactional
    public void edit(Cargo cargo) {
        cargoDao.edit(cargo);
    }

    @Override
    @Transactional
    public Cargo getById(int id) {
        return cargoDao.getById(id);
    }
}
