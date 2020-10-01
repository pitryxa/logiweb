package logiweb.service.api;

import logiweb.dto.DriverDto;
import logiweb.entity.Driver;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverService {
    List<DriverDto> getAll();

    void add(DriverDto driverDto);

    void delete(DriverDto driverDto);

    void edit(DriverDto driverDto);

    DriverDto getById(int id);

    LocalDateTime getTimeLastChangeStatusById(int id);
}
