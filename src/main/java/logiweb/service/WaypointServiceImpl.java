package logiweb.service;

import logiweb.converter.WaypointConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.api.TruckDao;
import logiweb.dao.api.WaypointDao;
import logiweb.dto.*;
import logiweb.entity.Cargo;
import logiweb.entity.Driver;
import logiweb.entity.Truck;
import logiweb.entity.enums.*;
import logiweb.service.api.DriverService;
import logiweb.service.api.TruckService;
import logiweb.service.api.WaypointService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WaypointServiceImpl implements WaypointService {
    private static final Logger logger = Logger.getLogger(WaypointServiceImpl.class);

    @Autowired
    private WaypointDao waypointDao;

    @Autowired
    private WaypointConverter waypointConverter;

    @Autowired
    private DriverService driverService;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CargoDao cargoDao;

    @Override
    public List<WaypointDto> getAll() {
        return waypointConverter.toListDto(waypointDao.getAll());
    }

    @Override
    @Transactional
    public void add(WaypointDto waypointDto) {
        waypointDao.create(waypointConverter.toEntity(waypointDto));
        logger.info("Waypoint is created.");
    }

    @Override
    @Transactional
    public void delete(WaypointDto waypointDto) {
        waypointDao.delete(waypointConverter.toEntity(waypointDto));
        logger.info("Waypoint is deleted.");
    }

    @Override
    @Transactional
    public void edit(WaypointDto waypointDto) {
        waypointDao.update(waypointConverter.toEntity(waypointDto));
        logger.info("Waypoint is updated.");
    }

    @Override
    public WaypointDto getById(int id) {
        return waypointConverter.toDto(waypointDao.getById(id));
    }

    @Override
    @Transactional
    public void doneWaypoint(int id, int orderId) {
        waypointDao.doneWaypoint(id);
        driverService.changeDriversStatusesInOrder(DriverStatus.SECOND_DRIVER);

        if (waypointDao.isUnloadWaypoint(id)) {
            Cargo cargo = waypointDao.getCargoByWaypointId(id);
            cargo.setStatus(CargoStatus.DELIVERED);
            cargoDao.update(cargo);
        }

        if (waypointDao.isAllWaypointsDone(orderId)) {
            orderDao.setStatus(orderId, OrderStatus.DONE);

            orderDao.getDrivers(orderId).forEach(d -> {
                d.setTruck(null);
                driverService.changeStatus(d, DriverStatus.RECREATION);
            });

            Truck truck = orderDao.getTruck(orderId);
            truck.setWorkStatus(TruckWorkStatus.FREE);
            truckDao.update(truck);
        }

        logger.info("Waypoint is complete.");
    }

    @Override
    public WaypointDto getCurrentWaypointFromOrder(OrderDto orderDto) {
        WaypointDto currentWaypoint = null;
        for (WaypointDto w : orderDto.getWaypoints()) {
            if (w.getStatus() == WaypointStatus.UNDONE) {
                currentWaypoint = w;
                break;
            }
        }
        return null;
    }
}
