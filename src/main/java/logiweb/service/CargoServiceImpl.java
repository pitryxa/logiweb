package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dto.CargoDto;
import logiweb.service.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoDao cargoDao;

    @Autowired
    private CargoConverter cargoConverter;

    @Override
    public List<CargoDto> getAll() {
        return cargoConverter.toListDto(cargoDao.getAllSorted());
    }

    @Override
    @Transactional
    public void add(CargoDto cargoDTO) {
        cargoDao.create(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public void delete(CargoDto cargoDTO) {
        cargoDao.delete(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public void edit(CargoDto cargoDTO) {
        cargoDao.update(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    public CargoDto getById(int id) {
        return cargoConverter.toDto(cargoDao.getById(id));
    }
}
