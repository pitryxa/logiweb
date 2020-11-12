package logiweb.converter;

import logiweb.dao.api.CityDao;
import logiweb.dto.DistanceDto;
import logiweb.entity.Distance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistanceConverter {

    @Autowired
    private CityDao cityDao;

    public DistanceDto toDto(Distance distance) {
        DistanceDto distanceDto = new DistanceDto();

        distanceDto.setId(distance.getId());
        distanceDto.setCityFrom(distance.getCityFrom().getName());
        distanceDto.setCityTo(distance.getCityTo().getName());
        distanceDto.setDistance(distance.getDistance());

        return distanceDto;
    }

    public Distance toEntity(DistanceDto distanceDto) {
        Distance distance = new Distance();

        distance.setId(distanceDto.getId());
        distance.setCityFrom(cityDao.getByName(distanceDto.getCityFrom()));
        distance.setCityTo(cityDao.getByName(distanceDto.getCityTo()));
        distance.setDistance(distanceDto.getDistance());

        return distance;
    }

    public List<DistanceDto> toListDto(List<Distance> distanceList) {
        return distanceList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
