package logiweb.converter;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.TruckDao;
import logiweb.dto.OrderDto;
import logiweb.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private WaypointConverter waypointConverter;

    @Autowired
    private DriverConverter driverConverter;


    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setTruck(truckConverter.toSimpleDto(order.getTruck()));
        orderDto.setStatus(order.getStatus());
        orderDto.setWaypoints(waypointConverter.toListDto(order.getWaypointEntities()));
        //orderDto.setDrivers(driverConverter.toListSimpleDto(order.getTruck().getDrivers()));

        orderDto.setDrivers(driverConverter.toListSimpleDto(driverDao.getByOrderId(order.getId())));

        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();

        order.setId(orderDto.getId());
        order.setStatus(orderDto.getStatus());
        order.setTruck(truckDao.getById(orderDto.getTruck().getId()));
        order.setWaypointEntities(waypointConverter.toListEntity(orderDto.getWaypoints()));

        return order;
    }

    public List<OrderDto> toListDto(List<Order> orders) {
        return orders
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
