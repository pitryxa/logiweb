package logiweb.service.api;

import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;

import java.util.List;

public interface CargoService {
    List<CargoDto> getAll();

    void add(CargoDto cargoDTO);

    void delete(CargoDto cargoDTO);

    void edit(CargoDto cargoDTO);

    CargoDto getById(int id);

    List<CargoDto> getPreparedCargo();

    List<CargoDto> getByListId(List<Integer> ids);

    Integer getOrderId(Cargo cargo);
}
