package app.tests;

import logiweb.converter.*;
import logiweb.dao.api.CargoDao;
import logiweb.dao.api.DriverDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.api.TruckDao;
import logiweb.service.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static app.tests.DataInit.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderConverter orderConverter;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        DataInit.setUpAll();
    }

    @Test
    public void testGetById() {
        when(orderDao.getById(any())).thenReturn(order);
        when(orderConverter.toDto(order)).thenReturn(orderDto);
        assertEquals(orderService.getById(order.getId()).getId(), orderDto.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetByIdNull() {
        when(orderDao.getById(any())).thenReturn(null);
        assertEquals(orderService.getById(order.getId()).getId(), orderDto.getId());
    }

    @Test
    public void testGetOrderByTruckId() {
        when(orderDao.getOrderByTruckId(any())).thenReturn(order);
        when(orderConverter.toDto(order)).thenReturn(orderDto);
        assertEquals(orderService.getOrderByTruckId(truck.getId()).getId(), orderDto.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetOrderByTruckIdNull() {
        when(orderDao.getOrderByTruckId(any())).thenReturn(null);
        assertEquals(orderService.getOrderByTruckId(truck.getId()).getId(), orderDto.getId());
    }

}
