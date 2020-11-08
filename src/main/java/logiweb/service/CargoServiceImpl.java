package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.entity.Order;
import logiweb.service.api.CargoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {
    private static final Logger logger = Logger.getLogger(CargoServiceImpl.class);

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
        logger.info("Cargo is created.");
    }

    @Override
    @Transactional
    public void delete(CargoDto cargoDTO) {
        cargoDao.delete(cargoConverter.toEntity(cargoDTO));
        logger.info("Cargo is deleted.");
    }

    @Override
    @Transactional
    public void edit(CargoDto cargoDTO) {
        cargoDao.update(cargoConverter.toEntity(cargoDTO));
        logger.info("Cargo is updated.");
    }

    @Override
    public CargoDto getById(int id) {
        return cargoConverter.toDto(cargoDao.getById(id));
    }

    @Override
    public List<CargoDto> getPreparedCargo() {
        List<Cargo> cargoList = cargoDao.getPreparedCargo();

        if (cargoList.isEmpty()) {
            logger.info("There is no prepared cargo.");
            return null;
        }

        return cargoConverter.toListDto(cargoList);
    }

    @Override
    public List<CargoDto> getByListId(List<Integer> ids) {
        return cargoConverter.toListDto(cargoDao.getByListId(ids));
    }

    @Override
    public Integer getOrderId(Cargo cargo) {
        Order order = cargoDao.getOrderByCargo(cargo);

        if (order == null) {
//            logger.info("The cargo not added to order.");
            return null;
        }

        return order.getId();
    }
}
