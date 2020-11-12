package logiweb.service;

import logiweb.converter.CityConverter;
import logiweb.dao.api.CityDao;
import logiweb.dto.CityDto;
import logiweb.entity.City;
import logiweb.service.api.CityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CityConverter cityConverter;

    @Override
    public List<CityDto> getAll() {
        return cityConverter.toListDto(cityDao.getAllSortedByName());
    }

    @Override
    @Transactional
    public void add(CityDto cityDto) {
        cityDao.create(cityConverter.toEntity(cityDto));
        logger.info("City is added.");
    }

    @Override
    @Transactional
    public void delete(CityDto cityDto) {
        cityDao.delete(cityConverter.toEntity(cityDto));
        logger.info("City is deleted.");
    }

    @Override
    @Transactional
    public void edit(CityDto cityDto) {
        cityDao.update(cityConverter.toEntity(cityDto));
        logger.info("City is updated.");
    }

    @Override
    public CityDto getById(int id) {
        return cityConverter.toDto(cityDao.getById(id));
    }

    @Override
    public CityDto getByName(String name) {
        City city = cityDao.getByName(name);

        if (city == null) {
            return null;
        }

        return cityConverter.toDto(city);
    }

    @Override
    public CityDto getCityByNameFromList(List<CityDto> cities, String cityName) {
        List<CityDto> cityDtoList =
                cities.stream()
                      .filter(cityDto -> cityDto.getName().equals(cityName))
                      .collect(Collectors.toList());

        return cityDtoList.isEmpty() ? null : cityDtoList.get(0);
    }
}
