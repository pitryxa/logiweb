package logiweb.service.api;

import logiweb.dto.CityDto;

import java.util.List;

public interface CityService {
    List<CityDto> getAll();

    void add(CityDto cityDto);

    void delete(CityDto cityDto);

    void edit(CityDto cityDto);

    CityDto getById(int id);

    CityDto getByName(String name);

    Long countOfCities();

    CityDto getCityByNameFromList(List<CityDto> cities, String cityName);
}
