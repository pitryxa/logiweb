package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.entity.Order;
import logiweb.entity.enums.CargoStatus;
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
        cargoDTO.setStatus(CargoStatus.PREPARED);
        cargoDao.create(cargoConverter.toEntity(cargoDTO));
        logger.info("Cargo is created.");
    }

    @Override
    @Transactional
    public void delete(CargoDto cargoDTO) {
        if (cargoDao.getById(cargoDTO.getId()) == null) {
            logger.info("The cargo is not deleted. This cargo is not exist.");
            throw new RuntimeException("This cargo is not exist.");
        }
        cargoDao.delete(cargoConverter.toEntity(cargoDTO));
        logger.info("Cargo is deleted.");
    }

    @Override
    @Transactional
    public void edit(CargoDto cargoDTO) {
        if (cargoDao.getById(cargoDTO.getId()) == null) {
            logger.info("The cargo is not updated. This cargo is not exist.");
            throw new RuntimeException("This cargo is not exist.");
        }
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
            return null;
        }

        return order.getId();
    }

    public void setCargoConverter(CargoConverter cargoConverter) {
        this.cargoConverter = cargoConverter;
    }
}
