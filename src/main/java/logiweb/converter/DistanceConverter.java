package logiweb.converter;

import logiweb.dto.CityDto;
import logiweb.dto.DistanceDto;
import logiweb.entity.City;
import logiweb.entity.Distance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistanceConverter {

    @Autowired
    private CityConverter cityConverter;

    public DistanceDto toDto(Distance distance) {
        DistanceDto distanceDto = new DistanceDto();

        distanceDto.setId(distance.getId());
        distanceDto.setCityFrom(cityConverter.toDto(distance.getCityFrom()));
        distanceDto.setCityTo(cityConverter.toDto(distance.getCityTo()));
        distanceDto.setDistance(distance.getDistance());

        return distanceDto;
    }

    public Distance toEntity(DistanceDto distanceDto) {
        Distance distance = new Distance();

        distance.setId(distanceDto.getId());
        distance.setCityFrom(cityConverter.toEntity(distanceDto.getCityFrom()));
        distance.setCityTo(cityConverter.toEntity(distanceDto.getCityTo()));
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
