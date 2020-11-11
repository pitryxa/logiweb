package logiweb.service.api;

import logiweb.service.calculating.Route;
import logiweb.dto.*;
import logiweb.dto.rest.DriverRestDto;
import logiweb.entity.Driver;
import logiweb.entity.enums.DriverStatus;

import java.util.List;

public interface DriverService {
    List<DriverDto> getAll();

    List<DriverDto> getAllNotDisabled();

    void add(DriverDto driverDto, int userId);

    void delete(DriverDto driverDto);

    void delete(DriverEditDto driverEditDto);

    void edit(DriverDto driverDto);

    void edit(DriverEditDto driverEditDto);

    DriverDto getById(int id);

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

    DriverRestDto getDriverRestDto();

    void disableDriver(DriverEditDto driverEditDto);

    DriverDto getByPersonalNumber(Long personalNumber);

    void setStatusAllDriversInOrder(DriverStatus newStatus);

    void enableDriver(int id);
}
