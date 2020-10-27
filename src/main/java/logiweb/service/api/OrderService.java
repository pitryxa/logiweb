package logiweb.service.api;

import logiweb.calculating.Route;
import logiweb.dto.CargoDto;
import logiweb.dto.DriverDto;
import logiweb.dto.OrderDto;
import logiweb.dto.TruckDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    void add(OrderDto orderDto);

    void add(List<CargoDto> cargoes, TruckDto truck, Route route, List<DriverDto> drivers);

    void delete(OrderDto orderDto);

    void edit(OrderDto orderDto);

    OrderDto getById(int id);

    OrderDto getOrderByTruckId(Integer truckId);
}
