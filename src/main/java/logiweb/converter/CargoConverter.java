package logiweb.converter;

import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CargoConverter {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityConverter cityConverter;

    public CargoDto toDto(Cargo cargo) {
        CargoDto cargoDTO = new CargoDto();

        cargoDTO.setId(cargo.getId());
        cargoDTO.setName(cargo.getName());
        cargoDTO.setWeight(cargo.getWeight());
        cargoDTO.setStatus(cargo.getStatus());
        cargoDTO.setCityFrom(cargo.getCityFrom().getName());
        cargoDTO.setCityTo(cargo.getCityTo().getName());

        return cargoDTO;
    }

    public Cargo toEntity(CargoDto cargoDTO) {
        Cargo cargo = new Cargo();

        cargo.setId(cargoDTO.getId());
        cargo.setName(cargoDTO.getName());
        cargo.setWeight(cargoDTO.getWeight());
        cargo.setStatus(cargoDTO.getStatus());
        cargo.setCityFrom(cityConverter.toEntity(cityService.getByName(cargoDTO.getCityFrom())));
        cargo.setCityTo(cityConverter.toEntity(cityService.getByName(cargoDTO.getCityTo())));

        return cargo;
    }

    public List<CargoDto> toListDto(List<Cargo> cargoList) {
        return cargoList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
