package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.converter.OrderConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.entity.Order;
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

    @Autowired
    private OrderConverter orderConverter;

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

    @Override
    public List<CargoDto> getPreparedCargo() {
        return cargoConverter.toListDto(cargoDao.getPreparedCargo());
    }

    @Override
    public List<CargoDto> getByListId(List<Integer> ids) {
        return cargoConverter.toListDto(cargoDao.getByListId(ids));
    }

    @Override
    public Integer getOrderId(Cargo cargo) {
        Order order = cargoDao.getOrderByCargo(cargo);
        return order == null ? null : order.getId();

    }
}
