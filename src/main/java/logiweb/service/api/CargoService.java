package logiweb.service.api;

import logiweb.dto.CargoDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CargoService {
    List<CargoDto> getAll();

    void add(CargoDto cargoDTO);

    void delete(CargoDto cargoDTO);

    void edit(CargoDto cargoDTO);

    CargoDto getById(int id);
}
