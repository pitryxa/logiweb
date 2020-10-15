package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.TruckDao;
import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.entity.Cargo;
import logiweb.entity.City;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TruckServiceImpl implements TruckService {

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
    public void add(TruckDto truckDto) {
        truckDao.create(truckConverter.toEntity(truckDto));
    }

    @Override
    @Transactional
    public void delete(TruckDto truckDto) {
        truckDao.delete(truckConverter.toEntity(truckDto));
    }

    @Override
    @Transactional
    public void edit(TruckDto truckDto) {
        truckDao.update(truckConverter.toEntity(truckDto));
    }

    @Override
    public TruckDto getById(int id) {
        return truckConverter.toDto(truckDao.getById(id));
    }

    @Override
    public List<TruckDto> getAllFreeTrucksInCity(City city) {
        return truckConverter.toListDto(truckDao.getAllFreeTrucksInCity(city));
    }

    @Override
    public List<TruckDto> getFreeTrucksByStartCityAndCapacityInCargoList(List<CargoDto> cargoes) {
        Map<City, Integer> cities = getStartCityAndSummaryWeightFromCargoes(cargoes);

        int maxWeight = Collections.max(cities.values());

        return truckConverter.toListDto(truckDao.getAllFreeTrucksInCities(cities.keySet(), maxWeight));
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
