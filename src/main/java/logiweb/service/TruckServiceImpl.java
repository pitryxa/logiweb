package logiweb.service;

import logiweb.aop.SendUpdate;
import logiweb.converter.CargoConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.TruckDao;
import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.dto.rest.TruckRestDto;
import logiweb.entity.Cargo;
import logiweb.entity.City;
import logiweb.entity.Order;
import logiweb.entity.Truck;
import logiweb.service.api.TruckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TruckServiceImpl implements TruckService {
    private static final Logger logger = Logger.getLogger(TruckServiceImpl.class);

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    private CargoConverter cargoConverter;

    @Override
    public List<TruckDto> getAll() {
        return truckConverter.toListDto(truckDao.getAllSorted());
    }

    @Override
    @Transactional
    @SendUpdate
    public void add(TruckDto truckDto) {
        truckDto.toUpperCaseRegNumber();
        truckDao.create(truckConverter.toEntity(truckDto));
        logger.info("Truck is added.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void delete(TruckDto truckDto) {
        truckDao.delete(truckConverter.toEntity(truckDto));
        logger.info("Truck is deleted.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void edit(TruckDto truckDto) {
        truckDto.toUpperCaseRegNumber();
        truckDao.update(truckConverter.toEntity(truckDto));
        logger.info("Truck is updated.");
    }

    @Override
    public TruckDto getById(int id) {
        Truck truck = truckDao.getById(id);
        return truck == null ? null : truckConverter.toDto(truck);
    }

    @Override
    public List<TruckDto> getAllFreeTrucksInCity(City city) {
        return truckConverter.toListDto(truckDao.getAllFreeTrucksInCity(city));
    }

    @Override
    public List<TruckDto> getFreeTrucksByStartCityAndCapacityInCargoList(List<CargoDto> cargoes) {
        Map<City, Integer> cities = getStartCityAndSummaryWeightFromCargoes(cargoes);

        int maxWeight = Collections.max(cities.values());

        List<Truck> freeTrucks = truckDao.getAllFreeTrucksInCities(cities.keySet(), maxWeight);

        if (freeTrucks.isEmpty()) {
            logger.info("No suitable trucks for order.");
            return new ArrayList<>();
        }

        return truckConverter.toListDto(freeTrucks);
    }

    @Override
    public Integer getOrderByTruck(Truck truck) {
        Order order = truckDao.getOrderByTruck(truck);
        return order == null ? null : order.getId();
    }

    @Override
    public TruckRestDto getTruckRestDto() {
        return new TruckRestDto(truckDao.getCountAllTrucks(), truckDao.getCountFreeTrucks(),
                                truckDao.getCountBrokenTrucks());
    }

    @Override
    public TruckDto getByRegNumber(String regNumber) {
        Truck truck = truckDao.getByRegNumber(regNumber);

        if (truck == null) {
            return null;
        }
        logger.info(String.format("The truck with reg. number %s exists.", regNumber));
        return truckConverter.toDto(truck);
    }

    private Map<City, Integer> getStartCityAndSummaryWeightFromCargoes(List<CargoDto> cargoes){
        Map<City, Integer> cities = new HashMap<>();
        List<Cargo> cargoList = cargoConverter.toListEntity(cargoes);

        for (Cargo cargo : cargoList) {
            City city = cargo.getCityFrom();
            int cargoWeight = cargo.getWeight();

            if (cities.containsKey(city)) {
                int curWeight = cities.get(city);
                cities.put(city, curWeight + cargoWeight);
            } else {
                cities.put(city, cargoWeight);
            }
        }

        return cities;
    }
}
