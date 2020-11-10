package logiweb.service;

import logiweb.aop.SendUpdate;
import logiweb.calculating.DriversCalc;
import logiweb.calculating.Route;
import logiweb.converter.DriverConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.api.UserDao;
import logiweb.dto.*;
import logiweb.dto.rest.DriverRestDto;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.User;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.OrderStatus;
import logiweb.entity.enums.Role;
import logiweb.service.api.DriverService;
import logiweb.service.api.OrderService;
import logiweb.service.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger logger = Logger.getLogger(DriverServiceImpl.class);

    final static int SECONDS_IN_HOUR = 3600;
    final static double MAX_WORK_HOURS = 176.0;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private DriversCalc driversCalc;

    @Override
    public List<DriverDto> getAll() {
        return driverConverter.toListDto(driverDao.getAllSorted());
    }

    @Override
    public List<DriverDto> getAllNotDisabled() {
        return driverConverter.toListDto(driverDao.getAllNotDisabled());
    }

    @Override
    @Transactional
    @SendUpdate
    public void add(DriverDto driverDto, int userId) {
        driverDto.setUser(userService.getById(userId));
        driverDto.setWorkHours(0.0);
        driverDto.setStatus(DriverStatus.RECREATION);
        driverDto.setTruck(null);
        driverDto.setTimeLastChangeStatus(LocalDateTime.now());
        driverDao.create(driverConverter.toEntity(driverDto));
        logger.info("Driver is added.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void delete(DriverDto driverDto) {
        driverDao.delete(driverConverter.toEntity(driverDto));
        logger.info("Driver is deleted.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void delete(DriverEditDto driverEditDto) {
        driverDao.delete(driverConverter.toEntity(driverEditDto));
        logger.info("Driver is deleted.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void edit(DriverDto driverDto) {
        driverDao.update(driverConverter.toEntity(driverDto));
        logger.info("Driver is updated.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void edit(DriverEditDto driverEditDto) {
        driverDao.update(driverConverter.toEntity(driverEditDto));
        logger.info("Driver is updated.");
    }

    @Override
    public DriverDto getById(int id) {
        return driverConverter.toDto(driverDao.getById(id));
    }

    @Override
    public boolean isNotEnoughDrivers(List<DriverDto> drivers, int shiftSize) {
        if (drivers.size() < shiftSize) {
            logger.info("Not enough drivers for order.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isWrongAmountDrivers(List<DriverDto> drivers, int shiftSize) {
        return drivers.size() != shiftSize;
    }

    @Override
    @Transactional
    @SendUpdate
    public List<DriverDto> getDriversForOrder(TruckDto truck, Route route) {
        double workHoursForEveryDriver = driversCalc.getWorkHoursForEveryDriver(truck, route);

        List<Driver> drivers = driverDao.getDriversByCityAndStatus(truck.getCity());

        List<Driver> suitableDrivers = drivers.stream()
                                              .peek(this::updateDriverWorkHoursInCurrentMonth)
                                              .filter(driver -> isAppropriateDriver(driver, workHoursForEveryDriver))
                                              .collect(Collectors.toList());

        return driverConverter.toListDto(suitableDrivers);
    }

    private void updateDriverWorkHoursInCurrentMonth(Driver driver) {
        LocalDateTime lastChange = driver.getTimeLastChangeStatus();
        LocalDateTime currentDate = LocalDateTime.now();

        if (!isCurrentMonth(lastChange, currentDate)) {
            driver.setWorkHours(0.0);
            driverDao.update(driver);
            logger.info("Hours worked by the driver in the current month have been updated.");
        }
    }

    private boolean isCurrentMonth(LocalDateTime firstTime, LocalDateTime secondTime) {
        String pattern = "yyyy MM";
        String firstTimeMonth = firstTime.format(DateTimeFormatter.ofPattern(pattern));
        String secondTimeMonth = secondTime.format(DateTimeFormatter.ofPattern(pattern));
        return firstTimeMonth.equals(secondTimeMonth);
    }

    private boolean isAppropriateDriver(Driver driver, double workHoursForEveryDriver) {
        return driver.getWorkHours() + workHoursForEveryDriver <= MAX_WORK_HOURS;
    }

    @Override
    public List<DriverDto> getByIdFromList(List<DriverDto> drivers, List<Integer> ids) {
        return drivers.stream().filter(d -> ids.contains(d.getId())).collect(Collectors.toList());
    }

    @Override
    public boolean isNotAssignedOrder() {
        String currentUserName = getCurrentUserName();
        int userId = userDao.getUserIdByEmail(currentUserName);
        Driver driver = driverDao.getByUserId(userId);

        return driver.getTruck() == null;
    }

    @Override
    public DriverDto getCurrentDriver() {
        String currentUserName = getCurrentUserName();
        int userId = userDao.getUserIdByEmail(currentUserName);

        return driverConverter.toDto(driverDao.getByUserId(userId));
    }

    @Override
    public OrderDto getOrderByDriver(DriverDto currentDriver) {
        return getOrderByTruckId(currentDriver.getTruck().getId());
    }

    @Override
    @Transactional
    @SendUpdate
    public void startExecuteOrder() {
        Driver driver = getCurrentDriverEntity();
        Order order = getOrderEntityByDriver(driver);

        changeStatus(driver, DriverStatus.ACTIVE_DRIVER);

        order.setStatus(OrderStatus.IN_PROGRESS);
        orderDao.update(order);
        logger.info("The order is accepted for execution.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void changeDriversStatusesInOrder(DriverStatus newStatusCurrentDriver) {
        Driver currentDriver = getCurrentDriverEntity();
        List<Driver> otherDrivers = currentDriver.getTruck()
                                                 .getDrivers()
                                                 .stream()
                                                 .filter(d -> !d.getId().equals(currentDriver.getId()))
                                                 .collect(Collectors.toList());

        DriverStatus currentStatus = currentDriver.getStatus();

        changeStatus(currentDriver, newStatusCurrentDriver);

        switch (currentStatus) {
            case SECOND_DRIVER:
                switch (newStatusCurrentDriver) {
                    case ACTIVE_DRIVER:
                        otherDrivers.forEach(d -> changeStatus(d, DriverStatus.SECOND_DRIVER));
                        break;

                    case LOAD_UNLOAD:
                        otherDrivers.forEach(d -> {
                            if (d.getStatus() == DriverStatus.ACTIVE_DRIVER) {
                                changeStatus(d, DriverStatus.SECOND_DRIVER);
                            }
                        });
                        break;

                    case SECOND_DRIVER:
                    default:
                        break;
                }
                break;

            case LOAD_UNLOAD:
                switch (newStatusCurrentDriver) {
                    case ACTIVE_DRIVER:
                        otherDrivers.forEach(d -> changeStatus(d, DriverStatus.SECOND_DRIVER));
                        break;

                    case SECOND_DRIVER:
                    case LOAD_UNLOAD:
                    default:
                        break;
                }
                break;

            case ACTIVE_DRIVER:
            default:
                break;
        }
    }

    @Override
    @Transactional
    @SendUpdate
    public void changeStatus(Driver driver, DriverStatus newStatus) {
        DriverStatus currentStatus = driver.getStatus();
        LocalDateTime now = LocalDateTime.now();

        switch (currentStatus) {
            case ACTIVE_DRIVER:
                if (newStatus == DriverStatus.SECOND_DRIVER || newStatus == DriverStatus.LOAD_UNLOAD ||
                    newStatus == DriverStatus.RECREATION) {
                    addWorkTimeToDriver(driver);
                }
                break;
            case LOAD_UNLOAD:
                if (newStatus == DriverStatus.SECOND_DRIVER || newStatus == DriverStatus.ACTIVE_DRIVER ||
                    newStatus == DriverStatus.RECREATION) {
                    addWorkTimeToDriver(driver);
                }
                break;
            case SECOND_DRIVER:
            default:
                break;
        }

        driver.setTimeLastChangeStatus(now);
        driver.setStatus(newStatus);
        driverDao.update(driver);
        String driverName = driver.getUser().getFirstName() + " " + driver.getUser().getLastName();
        logger.info(String.format("Status of driver %s (%d) is updated.", driverName, driver.getPersonalNumber()));
    }

    @Override
    public Integer getOrderId(Driver driver) {
        Order order = driverDao.getOrderByDriver(driver);

        if (order == null) {
//            logger.info(String.format("The driver %s (%d) has no active orders."));
            return null;
        }
        return order.getId();
    }

    @Override
    public DriverRestDto getDriverRestDto() {
        return new DriverRestDto(driverDao.getCountAllDrivers(), driverDao.getCountFreeDrivers());
    }

    @Override
    @Transactional
    public void disableDriver(DriverEditDto driverEditDto) {
        driverEditDto.setTimeLastChangeStatus(LocalDateTime.now());
        driverEditDto.setStatus(DriverStatus.DISABLED);

//        UserDto userDto = userService.getById(driverEditDto.getUserId());
//        userDto.setRole(Role.ROLE_NONE);
//        userService.edit(userDto);

        User user = userDao.getById(driverEditDto.getUserId());
        user.setRole(Role.ROLE_NONE);
        userDao.update(user);

        edit(driverEditDto);

        logger.info("Driver is disabled.");
    }

    @Override
    public DriverDto getByPersonalNumber(Long personalNumber) {
        Driver driver = driverDao.getByPersonalNumber(personalNumber);

        if (driver != null) {
            logger.info(String.format("The driver with personal number %d already exists.", personalNumber));
            return driverConverter.toDto(driver);
        }

        return null;
    }

    private void addWorkTimeToDriver(Driver driver) {
        LocalDateTime timeLastChange = driver.getTimeLastChangeStatus();
        double workTime = driversCalc.getWorkTime(timeLastChange);
        double currentWorkHours = driver.getWorkHours();

        if (!isCurrentMonth(timeLastChange, LocalDateTime.now())) {
            LocalDateTime firstDayCurrentMonth =
                    timeLastChange.with(TemporalAdjusters.firstDayOfNextMonth()).with(LocalTime.MIN);

            workTime -= (double) Duration.between(timeLastChange, firstDayCurrentMonth).getSeconds() / SECONDS_IN_HOUR;
            currentWorkHours = 0.0;
        }

        driver.setWorkHours(currentWorkHours + workTime);
    }

    private Order getOrderEntityByDriver(Driver driver) {
        return orderDao.getOrderByTruckId(driver.getTruck().getId());
    }

    private Driver getCurrentDriverEntity() {
        String currentUserName = getCurrentUserName();
        int userId = userDao.getUserIdByEmail(currentUserName);

        return driverDao.getByUserId(userId);
    }

    private OrderDto getOrderByTruckId(Integer truckId) {
        return orderService.getOrderByTruckId(truckId);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }

        return null;
    }


}
