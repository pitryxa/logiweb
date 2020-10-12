package logiweb.service.api;

import logiweb.dto.CityDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CityService {
    List<CityDto> getAll();

    void add(CityDto cityDto) throws UnsupportedEncodingException;

    void delete(CityDto cityDto);

    void edit(CityDto cityDto) throws UnsupportedEncodingException;

    CityDto getById(int id);

    CityDto getByName(String name);

    Long countOfCities();

    Integer[][] getMatrixOfDistances();

    CityDto getCityByNameFromList(List<CityDto> cities, String cityName);


}
