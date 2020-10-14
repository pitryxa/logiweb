package logiweb.service;

import logiweb.converter.CityConverter;
import logiweb.dao.api.CityDao;
import logiweb.dto.CityDto;
import logiweb.entity.Distance;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

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
    public CityDto getById(int id) {
        return cityConverter.toDto(cityDao.getById(id));
    }

    @Override
    public CityDto getByName(String name) {
        return cityConverter.toDto(cityDao.getByName(name));
    }

    @Override
    public Long countOfCities() {
        return cityDao.countOfCities();
    }

    @Override
    public Integer[][] getMatrixOfDistances() {
        int size = countOfCities().intValue();
        Integer[][] distances = new Integer[size][size];

        List<Distance> distanceList = cityDao.getDistanceList();

        for (Distance dist : distanceList) {
            int from = dist.getCityFrom().getId();
            int to = dist.getCityTo().getId();

            distances[from - 1][to - 1] = dist.getDistance();
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (distances[i][j] == null) {
                    distances[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        return distances;
    }

    @Override
    public CityDto getCityByNameFromList(List<CityDto> cities, String cityName) {
        for (CityDto city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }
}
