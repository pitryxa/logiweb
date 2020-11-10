package logiweb.converter;

import logiweb.dto.CityDto;
import logiweb.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityConverter {

    public CityDto toDto(City city) {
        CityDto cityDTO = new CityDto();

        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());

        return cityDTO;
    }

    public City toEntity(CityDto cityDTO) {
        City city = new City();

        city.setId(cityDTO.getId());
        city.setName(cityDTO.getName());

        return city;
    }

    public List<CityDto> toListDto(List<City> cityList) {
        return cityList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
