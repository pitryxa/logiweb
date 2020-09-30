package logiweb.service;

import logiweb.converter.CityConverter;
import logiweb.dao.api.CityDao;
import logiweb.dto.CityDto;
import logiweb.entity.City;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CityConverter cityConverter;

    @Override
    @Transactional
    public List<CityDto> getAll() {
        return this.createCityDtoListFromCityList(cityDao.getAll());
    }

    @Override
    @Transactional
    public void add(CityDto cityDto) {
        cityDao.create(cityConverter.toEntity(cityDto));
    }

    @Override
    @Transactional
    public void delete(CityDto cityDto) {
        cityDao.delete(cityConverter.toEntity(cityDto));
    }

    @Override
    @Transactional
    public void edit(CityDto cityDto) {
        cityDao.update(cityConverter.toEntity(cityDto));
    }

    @Override
    @Transactional
    public CityDto getById(int id) {
        return cityConverter.toDto(cityDao.getById(id));
    }

    @Override
    @Transactional
    public CityDto getByName(String name){
        return cityConverter.toDto(cityDao.getByName(name));
    }

    public List<CityDto> createCityDtoListFromCityList(List<City> cityList) {
        List<CityDto> cityDtoList = new ArrayList<>();

        for (City city : cityList) {
            cityDtoList.add(cityConverter.toDto(city));
        }

        return cityDtoList;
    }
}
