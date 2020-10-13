package logiweb.service;

import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
import logiweb.converter.DriverConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dto.CargoDto;
import logiweb.dto.CityDto;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.service.api.CityService;
import logiweb.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private CityService cityService;

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
    public List<DriverDto> getDriversForOrder(TruckDto truck, Route route) {
        CityDto homeCity = cityService.getByName(truck.getCity());

        final int workHoursPerDayForOneDriver = 8;
        final int shiftSize = truck.getShiftSize();

        double timeExecOrderInHours = getExecTimeForOrder(route);

        LocalDateTime now = LocalDateTime.now();
        Month monthBeforeOrder = now.getMonth();
        Month monthAfterOrder = now.plusMinutes((long) (timeExecOrderInHours * 60)).getMonth();

        if (!monthBeforeOrder.equals(monthAfterOrder)) {
            LocalDateTime firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth());

            double minutesForNextMonth = (double) Duration.between(now, firstDayOfNextMonth).getSeconds() / 60;

//            double timeExecOrderInCurrentMonth = minutesForNextMonth / 60;
        }



        //double timeWorkForEveryDriver = timeExecOrder / shiftSize;







        return null;
    }

    private double getExecTimeForOrder(Route route) {
        final int averageSpeed = 60;
        final int hoursForLoadUnload = 4;

        double fullTime = route.getDistance().doubleValue() / averageSpeed;

        for (Waypoint w : route.getWaypoints()) {
            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : w.getCargoes().entrySet()) {
                fullTime += hoursForLoadUnload;
            }
        }

        return fullTime;
    }


}
