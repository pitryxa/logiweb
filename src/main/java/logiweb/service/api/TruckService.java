package logiweb.service.api;

import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.dto.rest.TruckRestDto;
import logiweb.entity.City;
import logiweb.entity.Truck;

import java.util.List;

public interface TruckService {
    List<TruckDto> getAll();

    void add(TruckDto truckDto);

    void delete(TruckDto truckDto);

    void edit(TruckDto truckDto);

    TruckDto getById(int id);

    List<TruckDto> getAllFreeTrucksInCity(City city);

    List<TruckDto> getFreeTrucksByStartCityAndCapacityInCargoList(List<CargoDto> cargoes);

    Integer getOrderByTruck(Truck truck);

    TruckRestDto getTruckRestDto();
}
