package logiweb.converter;

import logiweb.dto.CargoDTO;
import logiweb.entity.Cargo;

public class CargoConverter {
    public static void toDto(Cargo cargo, CargoDTO cargoDTO) {
        cargoDTO.setId(cargo.getId());
        cargoDTO.setName(cargo.getName());
        cargoDTO.setWeight(cargo.getWeight());
        cargoDTO.setStatus(cargo.getStatus());
    }

    public static CargoDTO toDto(Cargo cargo) {
        CargoDTO cargoDTO = new CargoDTO();

        cargoDTO.setId(cargo.getId());
        cargoDTO.setName(cargo.getName());
        cargoDTO.setWeight(cargo.getWeight());
        cargoDTO.setStatus(cargo.getStatus());

        return cargoDTO;
    }

    public static void toEntity(Cargo cargo, CargoDTO cargoDTO) {
        cargo.setId(cargoDTO.getId());
        cargo.setName(cargoDTO.getName());
        cargo.setWeight(cargoDTO.getWeight());
        cargo.setStatus(cargoDTO.getStatus());
    }

    public static Cargo toEntity(CargoDTO cargoDTO) {
        Cargo cargo = new Cargo();

        cargo.setId(cargoDTO.getId());
        cargo.setName(cargoDTO.getName());
        cargo.setWeight(cargoDTO.getWeight());
        cargo.setStatus(cargoDTO.getStatus());

        return cargo;
    }
}
