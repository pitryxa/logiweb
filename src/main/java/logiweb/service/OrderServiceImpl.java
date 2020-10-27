package logiweb.service;

import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
import logiweb.converter.*;
import logiweb.dao.api.*;
import logiweb.dto.*;
import logiweb.entity.*;
import logiweb.entity.enums.*;
import logiweb.service.api.CargoService;
import logiweb.service.api.DriverService;
import logiweb.service.api.OrderService;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private TruckService truckService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    private CargoConverter cargoConverter;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private CargoDao cargoDao;

    @Autowired
    private TruckDao truckDao;

    @Override
    public List<OrderDto> getAll() {
        List<OrderDto> orderDtoList = orderConverter.toListDto(orderDao.getAllSorted());

        orderDtoList.stream()
                    .peek(order -> order.setWaypoints(order.getWaypoints()
                                                           .stream()
                                                           .sorted(Comparator.comparingInt(
                                                                   WaypointDto::getSequentialNumber))
                                                           .collect(Collectors.toList())))
                    .collect(Collectors.toList());


        return orderDtoList;
    }

    @Override
    @Transactional
    public void add(OrderDto orderDto) {
        orderDao.create(orderConverter.toEntity(orderDto));
    }

    @Override
    @Transactional
    public void delete(OrderDto orderDto) {
        orderDao.delete(orderConverter.toEntity(orderDto));
    }

    @Override
    @Transactional
    public void edit(OrderDto orderDto) {
        orderDao.update(orderConverter.toEntity(orderDto));
    }

    @Override
    public OrderDto getById(int id) {
        OrderDto orderDto = orderConverter.toDto(orderDao.getById(id));
        orderDto.setWaypoints(sortWaypointList(orderDto.getWaypoints()));
        return orderDto;
    }

    @Override
    public OrderDto getOrderByTruckId(Integer truckId) {
        OrderDto orderDto = orderConverter.toDto(orderDao.getOrderByTruckId(truckId));
        orderDto.setWaypoints(sortWaypointList(orderDto.getWaypoints()));
        return orderDto;
    }

    private List<WaypointDto> sortWaypointList(List<WaypointDto> list) {
        return list
                .stream()
                .sorted(Comparator.comparingInt(
                        WaypointDto::getSequentialNumber))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(List<CargoDto> cargoes, TruckDto truck, Route route, List<DriverDto> drivers) {
        List<Driver> driversEntity = driverConverter.toListEntityFromDto(drivers);
        Deque<Waypoint> wpFromRoute = route.getWaypoints();

        //update truck
        Truck truckEntity = truckConverter.toEntity(truck);
        truckEntity.setWorkStatus(TruckWorkStatus.IN_WAY);
        truckDao.update(truckEntity);

        //add order to db
        Order order = createOrder(truckEntity, route.getDistance());
        int count = 1;
        for (Waypoint w : wpFromRoute) {
            if (w.getCargoes().isEmpty()) {
                order.getWaypointEntities().add(getWaypointEntityFromCargo(wpFromRoute.getLast(), null, count, order));
                break;
            }

            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : w.getCargoes().entrySet()) {
                WaypointEntity waypointEntity = getWaypointEntityFromCargo(w, entry, count, order);
                order.getWaypointEntities().add(waypointEntity);
                count++;
            }

        }
        orderDao.create(order);
        final int orderId = orderDao.getOrderByTruckId(truck.getId()).getId();

        //update drivers
        driversEntity.stream().peek(d -> {
            d.setTruck(truckEntity);
            d.setStatus(DriverStatus.SECOND_DRIVER);
            orderDao.addToOrderDriversTable(orderId, d.getId());
        }).forEach(driver -> driverDao.update(driver));



    }

    private WaypointEntity getWaypointEntityFromCargo(Waypoint w, Map.Entry<CargoDto, OperationTypeOnWaypoint> entry,
                                                      int count, Order order) {
        WaypointEntity waypointEntity = new WaypointEntity();

        //update cargo status
        if (entry != null) {
            Cargo cargo = cargoConverter.toEntity(entry.getKey());
            cargo.setStatus(CargoStatus.SHIPPED);
            cargoDao.update(cargo);

            waypointEntity.setCargo(cargo);
            waypointEntity.setOperation(entry.getValue());
        } else {
            //waypointEntity.setCargo(null);
            waypointEntity.setOperation(OperationTypeOnWaypoint.NONE);
        }

        waypointEntity.setCity(cityConverter.toEntity(w.getCity()));
        waypointEntity.setOrder(order);
        waypointEntity.setSequentialNumber(count);
        waypointEntity.setStatus(WaypointStatus.UNDONE);

        return waypointEntity;
    }

    private Order createOrder(Truck truck, Integer distance) {
        Order order = new Order();
        order.setTruck(truck);
        order.setStatus(OrderStatus.PREPARED);
        order.setDistance(distance);
        order.setWaypointEntities(new LinkedList<>());
        return order;
    }
}
