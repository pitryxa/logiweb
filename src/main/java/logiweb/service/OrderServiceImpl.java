package logiweb.service;

import logiweb.converter.OrderConverter;
import logiweb.dao.api.OrderDao;
import logiweb.dto.OrderDto;
import logiweb.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderConverter orderConverter;

    @Override
    public List<OrderDto> getAll() {
        return orderConverter.toListDto(orderDao.getAllSorted());
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
        return orderConverter.toDto(orderDao.getById(id));
    }
}
