package logiweb.service.api;

import logiweb.dto.TruckDto;

import java.util.List;

public interface TruckService {
    List<TruckDto> getAll();

    void add(TruckDto truckDto);

    void delete(TruckDto truckDto);

    void edit(TruckDto truckDto);

    TruckDto getById(int id);
}
