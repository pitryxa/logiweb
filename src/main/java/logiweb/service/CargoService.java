package logiweb.service;

import logiweb.dto.CargoDTO;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CargoService {
    List<CargoDTO> allCargo();

    void add(CargoDTO cargoDTO) throws UnsupportedEncodingException;

    void delete(CargoDTO cargoDTO);

    void edit(CargoDTO cargoDTO) throws UnsupportedEncodingException;

    CargoDTO getById(int id);
}
