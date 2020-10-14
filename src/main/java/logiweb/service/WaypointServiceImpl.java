package logiweb.service;

import logiweb.calculating.Dijkstra;
import logiweb.calculating.Node;
import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
import logiweb.converter.WaypointConverter;
import logiweb.dao.api.WaypointDao;
import logiweb.dto.*;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.service.api.CityService;
import logiweb.service.api.DistanceService;
import logiweb.service.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WaypointServiceImpl implements WaypointService {

    @Autowired
    private WaypointDao waypointDao;

    @Autowired
    private WaypointConverter waypointConverter;

    @Override
    public List<WaypointDto> getAll() {
        return waypointConverter.toListDto(waypointDao.getAll());
    }

    @Override
    @Transactional
    public void add(WaypointDto waypointDto) {
        waypointDao.create(waypointConverter.toEntity(waypointDto));
    }

    @Override
    @Transactional
    public void delete(WaypointDto waypointDto) {
        waypointDao.delete(waypointConverter.toEntity(waypointDto));
    }

    @Override
    @Transactional
    public void edit(WaypointDto waypointDto) {
        waypointDao.update(waypointConverter.toEntity(waypointDto));
    }

    @Override
    public WaypointDto getById(int id) {
        return waypointConverter.toDto(waypointDao.getById(id));
    }
}
