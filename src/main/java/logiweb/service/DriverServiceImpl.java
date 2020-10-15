package logiweb.service;

import logiweb.calculating.DriversCalc;
import logiweb.calculating.Route;
import logiweb.converter.DriverConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private DriversCalc driversCalc;

    @Override
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
    public DriverDto getById(int id) {
        return driverConverter.toDto(driverDao.getById(id));
    }

    @Override
    public LocalDateTime getTimeLastChangeStatusById(int id) {
        return null;
    }

    @Override
    public boolean isNotEnoughDrivers(List<DriverDto> drivers, int shiftSize) {
        return drivers.size() < shiftSize;
    }

    @Override
    public boolean isWrongAmountDrivers(List<DriverDto> drivers, int shiftSize) {
        return drivers.size() != shiftSize;
    }

    @Override
    public List<DriverDto> getDriversForOrder(TruckDto truck, Route route) {
        double workHoursForEveryDriver = driversCalc.getWorkHoursForEveryDriver(truck, route);

        return driverConverter.toListDto(
                driverDao.getDriversByCityAndWorkTimeAndStatus(truck.getCity(), workHoursForEveryDriver));
    }

    @Override
    public List<DriverDto> getByIdFromList(List<DriverDto> drivers, List<Integer> ids) {
        return drivers.stream().filter(d -> ids.contains(d.getId())).collect(Collectors.toList());
    }
}
