package app.tests;

import logiweb.calculating.DriversCalc;
import logiweb.converter.DriverConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.api.UserDao;
import logiweb.dto.DriverDto;
import logiweb.service.DriverServiceImpl;
import logiweb.service.api.DriverService;
import logiweb.service.api.OrderService;
import logiweb.service.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.tests.DataInit.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {
    @Mock
    private DriverDao driverDao;

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private TruckConverter truckConverter;

    @Mock
    private DriverConverter driverConverter;

    @Mock
    private DriversCalc driversCalc;

    @InjectMocks
    private DriverServiceImpl driverServiceTest;

    @InjectMocks
    private ModelMapper mapper;

    @Before
    public void setUp() {
        DataInit.setUpAll();
        driverConverter.setMapper(mapper);
    }

    @Test
    public void testIsNotEnoughDrivers() {
        List<DriverDto> driverDtoList = Arrays.asList(firstDriverDto, secondDriverDto);

        assertFalse(driverServiceTest.isNotEnoughDrivers(driverDtoList, truckDto.getShiftSize()));
        assertTrue(driverServiceTest.isNotEnoughDrivers(driverDtoList, 111));
    }

    @Test
    public void testGetDriversForOrder() {
        when(driversCalc.getWorkHoursForEveryDriver(any(), any())).thenReturn(10.0);
        when(driverDao.getDriversByCityAndStatus(any())).thenReturn(Arrays.asList(firstDriver, secondDriver));
        when(driverConverter.toListDto(argThat(list -> list.size() == 2))).thenReturn(
                Arrays.asList(firstDriverDto, secondDriverDto));
        assertEquals(driverServiceTest.getDriversForOrder(truckDto, route).size(), 2);
    }

    @Test
    public void testGetDriversForOrderNone() {
        when(driverDao.getDriversByCityAndStatus(any())).thenReturn(Arrays.asList(firstDriver, secondDriver));
        when(driverConverter.toListDto(argThat(list -> list.size() == 0))).thenReturn(new ArrayList<>());
        when(driversCalc.getWorkHoursForEveryDriver(any(), any())).thenReturn(80.0);
        assertEquals(driverServiceTest.getDriversForOrder(truckDto, route).size(), 0);
    }


}
