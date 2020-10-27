package logiweb.converter;

import logiweb.dao.api.CityDao;
import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.service.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CargoConverter {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CargoService cargoService;

    public CargoDto toDto(Cargo cargo) {
        CargoDto cargoDTO = new CargoDto();

        cargoDTO.setId(cargo.getId());
        cargoDTO.setName(cargo.getName());
        cargoDTO.setWeight(cargo.getWeight());
        cargoDTO.setStatus(cargo.getStatus());
        cargoDTO.setCityFrom(cargo.getCityFrom().getName());
        cargoDTO.setCityTo(cargo.getCityTo().getName());

        cargoDTO.setOrderId(cargoService.getOrderId(cargo));

        return cargoDTO;
    }

    public Cargo toEntity(CargoDto cargoDTO) {
        Cargo cargo = new Cargo();

        cargo.setId(cargoDTO.getId());
        cargo.setName(cargoDTO.getName());
        cargo.setWeight(cargoDTO.getWeight());
        cargo.setStatus(cargoDTO.getStatus());
        cargo.setCityFrom(cityDao.getByName(cargoDTO.getCityFrom()));
        cargo.setCityTo(cityDao.getByName(cargoDTO.getCityTo()));

        return cargo;
    }

    public List<CargoDto> toListDto(List<Cargo> cargoList) {
        return cargoList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Cargo> toListEntity(List<CargoDto> cargoDtoList) {
        return cargoDtoList
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
