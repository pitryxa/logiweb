package logiweb.service;

import logiweb.converter.DriverConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dao.api.TruckDao;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.entity.Driver;
import logiweb.entity.Truck;
import logiweb.service.api.DriverService;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverConverter driverConverter;

    @Override
    @Transactional
    public List<DriverDto> getAll() {
        return driverConverter.toListDto(driverDao.getAllSorted());
    }

    @Override
    @Transactional
    public void add(DriverDto driverDto) {
        driverDao.create(driverConverter.toEntity(driverDto));
    }

    @Override
    @Transactional
    public void delete(DriverDto driverDto) {
        driverDao.delete(driverConverter.toEntity(driverDto));
    }

    @Override
    @Transactional
    public void edit(DriverDto driverDto) {
        driverDao.update(driverConverter.toEntity(driverDto));
    }

    @Override
    @Transactional
    public DriverDto getById(int id) {
        return driverConverter.toDto(driverDao.getById(id));
    }
}
