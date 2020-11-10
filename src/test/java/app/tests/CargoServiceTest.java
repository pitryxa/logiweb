package app.tests;

import logiweb.dao.api.CargoDao;
import logiweb.service.CargoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static app.tests.DataInit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {
    @Mock
    private CargoDao cargoDao;

    @InjectMocks
    private CargoServiceImpl cargoService;

    @Test(expected = NullPointerException.class)
    public void testGetPreparedCargoNull() {
        when(cargoDao.getPreparedCargo()).thenReturn(new ArrayList<>());
        assertEquals(cargoService.getPreparedCargo().size(), 0);
    }

    @Test
    public void testGetOrderIdByCargoNull() {
        when(cargoDao.getOrderByCargo(cargo)).thenReturn(null);
        assertEquals(cargoService.getOrderId(cargo),null);
    }


}
