package logiweb.converter;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.TruckDao;
import logiweb.dto.OrderDto;
import logiweb.dto.rest.OrderRestDto;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.WaypointEntity;
import logiweb.entity.enums.WaypointStatus;
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
        orderDto.setDrivers(driverConverter.toListSimpleDto(driverDao.getByOrderId(order.getId())));
//        orderDto.setTimeCreatedOrder(order.getTimeCreatedOrder());

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

    public OrderRestDto toRestDto(Order order) {
        OrderRestDto orderRestDto = new OrderRestDto();

        orderRestDto.setId(order.getId());
        orderRestDto.setStatus(order.getStatus().name());
        orderRestDto.setTruck(order.getTruck().getRegNumber());
        orderRestDto.setDrivers(getDriversNames(driverDao.getByOrderId(order.getId())));
        orderRestDto.setCurrentWaypoint(getCurrentWaypoint(order.getWaypointEntities()));

        return orderRestDto;
    }

    private String getCurrentWaypoint(List<WaypointEntity> waypoints) {
        for (WaypointEntity w : waypoints) {
            if (w.getStatus() == WaypointStatus.UNDONE) {
                return w.getCity().getName();
            }
        }

        return null;
    }

    private List<String> getDriversNames(List<Driver> drivers) {
        return drivers.stream()
                      .map(driver -> driver.getUser().getFirstName() + " " + driver.getUser().getLastName() + " (" +
                                     driver.getPersonalNumber() + ")")
                      .collect(Collectors.toList());
    }

    public List<OrderDto> toListDto(List<Order> orders) {
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<OrderRestDto> toListRestDto(List<Order> orders) {
        return orders.stream().map(this::toRestDto).collect(Collectors.toList());
    }

}
