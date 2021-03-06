package logiweb.service;

import logiweb.aop.SendUpdate;
import logiweb.service.calculating.Route;
import logiweb.service.calculating.Waypoint;
import logiweb.converter.*;
import logiweb.dao.api.*;
import logiweb.dto.*;
import logiweb.dto.rest.OrderRestDto;
import logiweb.entity.*;
import logiweb.entity.enums.*;
import logiweb.service.api.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderConverter orderConverter;

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

    @Autowired
    private WaypointDao waypointDao;

    @Override
    public List<OrderDto> getAll() {
        List<OrderDto> orderDtoList = orderConverter.toListDto(orderDao.getAllSorted());
        return sortWaypointsInOrder(orderDtoList);
    }

    private List<OrderDto> sortWaypointsInOrder(List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                           .peek(order -> order.setWaypoints(order.getWaypoints()
                                                                  .stream()
                                                                  .sorted(Comparator.comparingInt(
                                                                          WaypointDto::getSequentialNumber))
                                                                  .collect(Collectors.toList())))
                           .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @SendUpdate
    public void add(OrderDto orderDto) {
        orderDao.create(orderConverter.toEntity(orderDto));
        logger.info("Order is created.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void delete(OrderDto orderDto) {
        orderDao.delete(orderConverter.toEntity(orderDto));
        logger.info("Order is deleted.");
    }

    @Override
    @Transactional
    @SendUpdate
    public void edit(OrderDto orderDto) {
        orderDao.update(orderConverter.toEntity(orderDto));
        logger.info("Order is updated.");
    }

    @Override
    public OrderDto getById(int id) {
        Order order = orderDao.getById(id);

        if (order == null) {
            return null;
        }

        OrderDto orderDto = orderConverter.toDto(order);
        orderDto.setWaypoints(sortWaypointList(orderDto.getWaypoints()));
        return orderDto;
    }

    @Override
    public OrderDto getOrderByTruckId(Integer truckId) {
        Order order = orderDao.getOrderByTruckId(truckId);

        if (order == null) {
            return null;
        }

        OrderDto orderDto = orderConverter.toDto(order);
        orderDto.setWaypoints(sortWaypointList(orderDto.getWaypoints()));
        return orderDto;
    }

    @Override
    public List<OrderRestDto> getTenLast() {
        return orderConverter.toListRestDto(orderDao.getTenLast()
                                                    .stream()
                                                    .peek(order -> order.setWaypointEntities(order.getWaypointEntities()
                                                                                                  .stream()
                                                                                                  .sorted(Comparator.comparingInt(
                                                                                                          WaypointEntity::getSequentialNumber))
                                                                                                  .collect(
                                                                                                          Collectors.toList())))
                                                    .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (orderDao.getById(id) == null) {
            logger.info("Order is not deleted. This order is no exists.");
            throw new RuntimeException("This order is no exists.");
        }

        List<WaypointEntity> waypoints = waypointDao.getAllByOrderId(id);
        Set<Cargo> cargoes = waypoints.stream().map(w -> w.getCargo()).collect(Collectors.toSet());
        Truck truck = orderDao.getTruck(id);
        List<Driver> drivers = driverDao.getByOrderId(id);

        truck.setWorkStatus(TruckWorkStatus.FREE);
        truckDao.update(truck);

        for (Driver d : drivers) {
            d.setTruck(null);
            d.setStatus(DriverStatus.RECREATION);
            driverDao.update(d);
        }

        for (Cargo c : cargoes) {
            if (c == null) {
                continue;
            }

            c.setStatus(CargoStatus.PREPARED);
            cargoDao.update(c);
        }

        orderDao.deleteById(id);
        logger.info("Order is deleted");
    }

    private List<WaypointDto> sortWaypointList(List<WaypointDto> list) {
        return list.stream()
                   .sorted(Comparator.comparingInt(WaypointDto::getSequentialNumber))
                   .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @SendUpdate
    public void add(List<CargoDto> cargoes, TruckDto truck, Route route, List<DriverDto> drivers) {
        if (!isExistAllItemsForOrder(cargoes, truck, drivers)) {
            logger.info("Order is not created. Something went wrong.");
            throw new RuntimeException("Something went wrong! Try to create the order again.");
        }

        List<Driver> driversEntity = driverConverter.toListEntityFromDto(drivers);
        Deque<Waypoint> wpFromRoute = route.getWaypoints();

        //update truck
        Truck truckEntity = truckConverter.toEntity(truck);
        truckEntity.setWorkStatus(TruckWorkStatus.IN_WAY);
        truckDao.update(truckEntity);

        //add order to db
        Order order = createOrder(truckEntity, route.getDistance(), wpFromRoute);
        orderDao.create(order);
        final int orderId = order.getId();

        //update drivers
        driversEntity.stream().peek(d -> {
            d.setTruck(truckEntity);
            d.setStatus(DriverStatus.SECOND_DRIVER);
            orderDao.addToOrderDriversTable(orderId, d.getId());
        }).forEach(driver -> driverDao.update(driver));

        logger.info("Order is created.");
    }

    private boolean isExistAllItemsForOrder(List<CargoDto> cargoes, TruckDto truck, List<DriverDto> drivers) {
        List<Integer> cargoIds = cargoes.stream().map(CargoDto::getId).collect(Collectors.toList());
        for (Integer id : cargoIds) {
            if (cargoDao.getPreparedCargoById(id) == null) {
                return false;
            }
        }

        if (truckDao.getFreeTruckById(truck.getId()) == null) {
            return false;
        }

        List<Integer> driverIds = drivers.stream().map(DriverDto::getId).collect(Collectors.toList());
        for (Integer id : driverIds) {
            if (driverDao.getFreeDriverById(id) == null) {
                return false;
            }
        }

        return true;
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
            waypointEntity.setOperation(OperationTypeOnWaypoint.NONE);
        }

        waypointEntity.setCity(cityConverter.toEntity(w.getCity()));
        waypointEntity.setOrder(order);
        waypointEntity.setSequentialNumber(count);
        waypointEntity.setStatus(WaypointStatus.UNDONE);

        return waypointEntity;
    }

    private Order createOrder(Truck truck, Integer distance, Deque<Waypoint> wpFromRoute) {
        Order order = new Order();
        order.setTruck(truck);
        order.setStatus(OrderStatus.PREPARED);
        order.setDistance(distance);
        order.setWaypointEntities(new LinkedList<>());

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

        return order;
    }
}
