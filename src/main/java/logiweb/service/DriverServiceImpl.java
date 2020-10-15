package logiweb.service;

import logiweb.calculating.Route;
import logiweb.converter.DriverConverter;
import logiweb.dao.api.CityDao;
import logiweb.dao.api.DriverDao;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    static final int MINUTES_IN_HOUR = 60;
    static final int SECONDS_IN_MINUTE = 60;
    static final int WORK_HOURS_PER_DAY_FOR_ONE_DRIVER = 8;
    static final int HOURS_IN_DAY = 24;
    static final int HOURS_FOR_LOAD_UNLOAD = 4;
    static final int AVERAGE_SPEED = 60;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private CityDao cityDao;

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
        final int shiftSize = truck.getShiftSize();

        double timeExecOrderInHours = getExecTimeForOrder(route);

        double workHoursForEveryDriver = getWorkHoursForEveryDriver(shiftSize, timeExecOrderInHours);

        return driverConverter.toListDto(
                driverDao.getDriversByCityAndWorkTimeAndStatus(truck.getCity(), workHoursForEveryDriver));
    }

    @Override
    public List<DriverDto> getByIdFromList(List<DriverDto> drivers, List<Integer> ids) {
        return drivers
                .stream()
                .filter(d -> ids.contains(d.getId()))
                .collect(Collectors.toList());
    }

    private double getWorkHoursForEveryDriver(int shiftSize, double timeExecOrderInHours) {
        if (shiftSize < 3) {
            timeExecOrderInHours = getTimeExecOrderInHoursDependShiftSize(shiftSize, timeExecOrderInHours);
        }

        timeExecOrderInHours = getTimeExecOrderInCurrentMonth(timeExecOrderInHours);

        double workHoursInTimeExecOrder = timeExecOrderInHours;
        if (shiftSize < 3) {
            workHoursInTimeExecOrder = getWorkHoursFromTimeExecOrder(timeExecOrderInHours, shiftSize);
        }
        return workHoursInTimeExecOrder / shiftSize;
    }

    private double getTimeExecOrderInCurrentMonth(double timeExecOrderInHours) {
        double timeExecOrderInMinutes = timeExecOrderInHours * MINUTES_IN_HOUR;

        LocalDateTime now = LocalDateTime.now();
        //now = LocalDateTime.of(2020, Month.OCTOBER, 30, 10, 0);

        Month monthBeforeOrder = now.getMonth();
        Month monthAfterOrder = now.plusMinutes((long) (timeExecOrderInMinutes)).getMonth();

        if (!monthBeforeOrder.equals(monthAfterOrder)) {
            LocalDateTime firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth()).with(LocalTime.MIN);
            timeExecOrderInMinutes =
                    (double) Duration.between(now, firstDayOfNextMonth).getSeconds() / SECONDS_IN_MINUTE;
            timeExecOrderInHours = timeExecOrderInMinutes / MINUTES_IN_HOUR;
        }

        return timeExecOrderInHours;
    }

    private double getWorkHoursFromTimeExecOrder(double timeExecOrderInHours, int shiftSize) {
        int workDays = (int) (timeExecOrderInHours / HOURS_IN_DAY);
        double remainderHours = timeExecOrderInHours % HOURS_IN_DAY;
        int workHoursPerDay = WORK_HOURS_PER_DAY_FOR_ONE_DRIVER * shiftSize;

        if (remainderHours >= workHoursPerDay) {
            workDays++;
            remainderHours -= workHoursPerDay;
        }

        return workDays * workHoursPerDay + remainderHours;
    }

    private double getTimeExecOrderInHoursDependShiftSize(int shiftSize, double timeExecOrderInHours) {
        int workHoursPerDay = WORK_HOURS_PER_DAY_FOR_ONE_DRIVER * shiftSize;

        int timeExecOrderInDays = (int) (timeExecOrderInHours / workHoursPerDay);
        double remainderHours = timeExecOrderInHours % workHoursPerDay;

        if (remainderHours >= workHoursPerDay) {
            timeExecOrderInDays++;
            remainderHours -= workHoursPerDay;
        }

        return timeExecOrderInDays * HOURS_IN_DAY + remainderHours;
    }

    private double getExecTimeForOrder(Route route) {

        final int NUMBER_WAYPOINTS_WITH_CARGO = route.getWaypoints().size() - 1;

        double currentTime = route.getDistance().doubleValue() / AVERAGE_SPEED;
        final double[] fullTime = {currentTime};

        return route.getWaypoints()
                    .stream()
                    .limit(NUMBER_WAYPOINTS_WITH_CARGO)
                    .map(w -> w.getCargoes()
                               .entrySet()
                               .stream()
                               .map(o -> fullTime[0] = addTimeForLoadUnload(fullTime[0]))
                               .reduce((first, last) -> last)
                               .orElse(currentTime))
                    .reduce((first, last) -> last)
                    .orElse(currentTime);
    }

    private Double addTimeForLoadUnload(double fullTime) {
        return fullTime + HOURS_FOR_LOAD_UNLOAD;
    }
}
