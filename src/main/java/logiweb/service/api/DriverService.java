package logiweb.service.api;

import logiweb.calculating.Route;
import logiweb.dto.*;
import logiweb.entity.Driver;
import logiweb.entity.enums.DriverStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverService {
    List<DriverDto> getAll();

    List<DriverDto> getAllNotDisabled();

    void add(DriverDto driverDto);

    void delete(DriverDto driverDto);

    void delete(DriverEditDto driverEditDto);

    void edit(DriverDto driverDto);

    void edit(DriverEditDto driverEditDto);

    DriverDto getById(int id);

    LocalDateTime getTimeLastChangeStatusById(int id);

    List<DriverDto> getDriversForOrder(TruckDto truck, Route route);

    boolean isNotEnoughDrivers(List<DriverDto> drivers, int shiftSize);

    boolean isWrongAmountDrivers(List<DriverDto> drivers, int shiftSize);

    List<DriverDto> getByIdFromList(List<DriverDto> drivers, List<Integer> ids);

    boolean isNotAssignedOrder();

    DriverDto getCurrentDriver();

    OrderDto getOrderByDriver(DriverDto currentDriver);

    void startExecuteOrder();

    void changeDriversStatusesInOrder(DriverStatus status);

    void changeStatus(Driver driver, DriverStatus newStatus);

    Integer getOrderId(Driver driver);
}
