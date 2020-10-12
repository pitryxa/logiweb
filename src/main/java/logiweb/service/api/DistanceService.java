package logiweb.service.api;

import logiweb.dto.CityDto;
import logiweb.dto.DistanceDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DistanceService {
    List<DistanceDto> getAll();

    void add(DistanceDto distanceDto);

    void delete(DistanceDto distanceDto);

    void edit(DistanceDto distanceDto);

    DistanceDto getById(int id);
}
