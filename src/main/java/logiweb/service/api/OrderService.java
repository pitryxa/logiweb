package logiweb.service.api;

import logiweb.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    void add(OrderDto orderDto);

    void delete(OrderDto orderDto);

    void edit(OrderDto orderDto);

    OrderDto getById(int id);
}
