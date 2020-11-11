package app.tests;

import logiweb.service.calculating.DriversCalc;
import logiweb.converter.DriverConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dao.api.OrderDao;
import logiweb.dao.api.UserDao;
import logiweb.dto.DriverDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.service.DriverServiceImpl;
import logiweb.service.api.OrderService;
import logiweb.service.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.tests.DataInit.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
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

    @Spy
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

    @Test
    public void testChangeDriversStatusesInOrder() {
        doReturn(firstDriver).when(driverServiceTest).getCurrentDriverEntity();

        firstDriver.setStatus(DriverStatus.SECOND_DRIVER);
        driverServiceTest.changeDriversStatusesInOrder(DriverStatus.ACTIVE_DRIVER);
        assertEquals(firstDriver.getStatus(), DriverStatus.ACTIVE_DRIVER);
        assertEquals(secondDriver.getStatus(), DriverStatus.SECOND_DRIVER);

        firstDriver.setStatus(DriverStatus.SECOND_DRIVER);
        secondDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeDriversStatusesInOrder(DriverStatus.LOAD_UNLOAD);
        assertEquals(firstDriver.getStatus(), DriverStatus.LOAD_UNLOAD);
        assertEquals(secondDriver.getStatus(), DriverStatus.SECOND_DRIVER);

        firstDriver.setStatus(DriverStatus.SECOND_DRIVER);
        secondDriver.setStatus(DriverStatus.LOAD_UNLOAD);
        driverServiceTest.changeDriversStatusesInOrder(DriverStatus.LOAD_UNLOAD);
        assertEquals(firstDriver.getStatus(), DriverStatus.LOAD_UNLOAD);
        assertEquals(secondDriver.getStatus(), DriverStatus.LOAD_UNLOAD);

        firstDriver.setStatus(DriverStatus.LOAD_UNLOAD);
        secondDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeDriversStatusesInOrder(DriverStatus.ACTIVE_DRIVER);
        assertEquals(firstDriver.getStatus(), DriverStatus.ACTIVE_DRIVER);
        assertEquals(secondDriver.getStatus(), DriverStatus.SECOND_DRIVER);
    }

    @Test
    public void testChangeStatus() {
        when(driversCalc.getWorkTime(any())).thenReturn(10.0);

        firstDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.ACTIVE_DRIVER);
        assertEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.SECOND_DRIVER);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.LOAD_UNLOAD);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.ACTIVE_DRIVER);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.RECREATION);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.LOAD_UNLOAD);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.ACTIVE_DRIVER);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.LOAD_UNLOAD);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.SECOND_DRIVER);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.LOAD_UNLOAD);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.RECREATION);
        assertNotEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());

        setUpFirstDriver();
        firstDriver.setStatus(DriverStatus.SECOND_DRIVER);
        driverServiceTest.changeStatus(firstDriver, DriverStatus.ACTIVE_DRIVER);
        assertEquals(firstDriver.getWorkHours(), firstDriverDto.getWorkHours());
    }

    @Test
    public void testGetOrderId() {
        when(driverDao.getOrderByDriver(any())).thenReturn(order);
        assertEquals(driverServiceTest.getOrderId(firstDriver), firstDriverDto.getId());
    }

    @Test
    public void testGetOrderIdNull() {
        when(driverDao.getOrderByDriver(any())).thenReturn(null);
        assertNull(driverServiceTest.getOrderId(firstDriver));
    }

    @Test
    public void testGetByPersonalNumber() {
        when(driverDao.getByPersonalNumber(any())).thenReturn(firstDriver);
        when(driverConverter.toDto(firstDriver)).thenReturn(firstDriverDto);
        assertEquals(driverServiceTest.getByPersonalNumber(firstDriver.getPersonalNumber()).getId(), firstDriverDto.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetByPersonalNumberNull() {
        when(driverDao.getByPersonalNumber(any())).thenReturn(null);
        assertEquals(driverServiceTest.getByPersonalNumber(firstDriver.getPersonalNumber()).getId(), firstDriverDto.getId());
    }

    @Test
    public void testIsWrongAmountDrivers() {
        List<DriverDto> drivers = Arrays.asList(firstDriverDto, secondDriverDto);
        assertTrue(driverServiceTest.isWrongAmountDrivers(drivers, 1));
        assertTrue(driverServiceTest.isWrongAmountDrivers(drivers, 3));
        assertFalse(driverServiceTest.isWrongAmountDrivers(drivers, 2));
    }


}
