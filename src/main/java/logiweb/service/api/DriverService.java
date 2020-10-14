package logiweb.service.api;

import logiweb.calculating.Route;
import logiweb.dto.CargoDto;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverService {
    List<DriverDto> getAll();

    void add(DriverDto driverDto);

    void delete(DriverDto driverDto);

    void edit(DriverDto driverDto);

    DriverDto getById(int id);

    LocalDateTime getTimeLastChangeStatusById(int id);

    List<DriverDto> getDriversForOrder(TruckDto truck, Route route);

    boolean isNotEnoughDrivers(List<DriverDto> drivers, int shiftSize);

    boolean isWrongAmountDrivers(List<DriverDto> drivers, int shiftSize);

    List<DriverDto> getByIdFromList(List<DriverDto> drivers, List<Integer> ids);
}
