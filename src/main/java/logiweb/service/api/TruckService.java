package logiweb.service.api;

import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.entity.City;

import java.util.List;

public interface TruckService {
    List<TruckDto> getAll();

    void add(TruckDto truckDto);

    void delete(TruckDto truckDto);

    void edit(TruckDto truckDto);

    TruckDto getById(int id);

    List<TruckDto> getAllFreeTrucksInCity(City city);

    List<TruckDto> getFreeTrucksByStartCityAndCapacityInCargoList(List<CargoDto> cargoes);

}
