package logiweb.converter;

import logiweb.dao.api.CityDao;
import logiweb.dto.TruckDto;
import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.Truck;
import logiweb.service.api.CityService;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TruckConverter {

    @Autowired
    private TruckService truckService;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private CityDao cityDao;

    public TruckDto toDto(Truck truck) {
        TruckDto truckDto = new TruckDto();

        truckDto.setId(truck.getId());
        truckDto.setRegNumber(truck.getRegNumber());
        truckDto.setShiftSize(truck.getShiftSize());
        truckDto.setCapacity(truck.getCapacity());
        truckDto.setConditionStatus(truck.getConditionStatus());
        truckDto.setWorkStatus(truck.getWorkStatus());
        truckDto.setCity(truck.getCity().getName());

        truckDto.setDrivers(
                truck.getDrivers().isEmpty()
                        ? null
                        : driverConverter.toListSimpleDto(truck.getDrivers())
        );

        truckDto.setOrderId(truckService.getOrderByTruck(truck));

        return truckDto;
    }

    public TruckDto toDto(SimpleTruckDto truck) {
        return truckService.getById(truck.getId());
    }

    public SimpleTruckDto toSimpleDto(Truck truck) {
        SimpleTruckDto truckDto = new SimpleTruckDto();

        truckDto.setId(truck.getId());
        truckDto.setRegNumber(truck.getRegNumber());

        return truckDto;
    }

    public Truck toEntity(TruckDto truckDto) {
        Truck truck = new Truck();

        truck.setId(truckDto.getId());
        truck.setRegNumber(truckDto.getRegNumber());
        truck.setShiftSize(truckDto.getShiftSize());
        truck.setCapacity(truckDto.getCapacity());
        truck.setConditionStatus(truckDto.getConditionStatus());
        truck.setWorkStatus(truckDto.getWorkStatus());

        truck.setDrivers(
                truckDto.getDrivers() == null
                ? null
                : driverConverter.toListEntity(truckDto.getDrivers())
        );

        truck.setCity(cityDao.getByName(truckDto.getCity()));

        return truck;
    }

    public Truck toEntity(SimpleTruckDto simpleTruckDto) {
        return toEntity(toDto(simpleTruckDto));
    }

    public List<TruckDto> toListDto(List<Truck> trucks) {
        return trucks
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
