package logiweb.converter;

import logiweb.dao.api.CityDao;
import logiweb.dao.api.OrderDao;
import logiweb.dto.WaypointDto;
import logiweb.entity.WaypointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WaypointConverter {
    @Autowired
    private CargoConverter cargoConverter;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CityDao cityDao;

    public WaypointDto toDto(WaypointEntity waypointEntity) {
        WaypointDto waypointDto = new WaypointDto();

        waypointDto.setId(waypointEntity.getId());
        waypointDto.setCargo(cargoConverter.toDto(waypointEntity.getCargo()));
        waypointDto.setCity(waypointEntity.getCity().getName());
        waypointDto.setOperation(waypointEntity.getOperation());
        waypointDto.setSequentialNumber(waypointEntity.getSequentialNumber());
        waypointDto.setOrderId(waypointEntity.getOrder().getId());

        return waypointDto;
    }

    public WaypointEntity toEntity(WaypointDto waypointDto) {
        WaypointEntity waypointEntity = new WaypointEntity();

        waypointEntity.setId(waypointDto.getId());
        waypointEntity.setOrder(orderDao.getById(waypointDto.getOrderId()));
        waypointEntity.setCargo(cargoConverter.toEntity(waypointDto.getCargo()));
        waypointEntity.setCity(cityDao.getByName(waypointDto.getCity()));
        waypointEntity.setOperation(waypointDto.getOperation());
        waypointEntity.setSequentialNumber(waypointDto.getSequentialNumber());

        return waypointEntity;
    }

    public List<WaypointDto> toListDto(List<WaypointEntity> waypointEntities) {
        return waypointEntities
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<WaypointEntity> toListEntity(List<WaypointDto> waypointsDto){
        return waypointsDto
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
