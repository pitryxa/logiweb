package logiweb.service.api;

import logiweb.calculating.Route;
import logiweb.dto.*;
import logiweb.dto.rest.OrderRestDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    void add(OrderDto orderDto);

    void add(List<CargoDto> cargoes, TruckDto truck, Route route, List<DriverDto> drivers);

    void delete(OrderDto orderDto);

    void edit(OrderDto orderDto);

    OrderDto getById(int id);

    OrderDto getOrderByTruckId(Integer truckId);

    List<OrderRestDto> getTenLast();
}
