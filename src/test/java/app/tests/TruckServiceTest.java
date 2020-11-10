package app.tests;

import logiweb.converter.CargoConverter;
import logiweb.converter.TruckConverter;
import logiweb.dao.api.TruckDao;
import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.entity.Cargo;
import logiweb.entity.Truck;
import logiweb.service.TruckServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static app.tests.DataInit.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TruckServiceTest {

    @Mock
    private TruckDao truckDao;

    @Mock
    private TruckConverter truckConverter;

    @Mock
    private CargoConverter cargoConverter;

    @InjectMocks
    private TruckServiceImpl truckService;

    @Before
    public void setUp() {
        DataInit.setUpAll();
    }

    @Test
    public void testGetById() {
        when(truckDao.getById(any())).thenReturn(truck);
        when(truckConverter.toDto(truck)).thenReturn(truckDto);
        assertEquals(truckService.getById(truck.getId()).getId(), truckDto.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetByIdNull() {
        when(truckDao.getById(any())).thenReturn(null);
        assertEquals(truckService.getById(truck.getId()).getId(), truckDto.getId());
    }

    @Test
    public void testGetFreeTrucksByStartCityAndCapacityInCargoList() {
        List<CargoDto> cargoDtoList = Collections.singletonList(cargoDto);
        List<Cargo> cargoList = Collections.singletonList(cargo);
        List<Truck> truckList = Collections.singletonList(truck);
        List<TruckDto> truckDtoList = Collections.singletonList(truckDto);

        when(cargoConverter.toListEntity(cargoDtoList)).thenReturn(cargoList);
        when(truckDao.getAllFreeTrucksInCities(anySet(), anyInt())).thenReturn(truckList);
        when(truckConverter.toListDto(truckList)).thenReturn(truckDtoList);

        assertEquals(truckService.getFreeTrucksByStartCityAndCapacityInCargoList(cargoDtoList).size(), 1);
    }

    @Test
    public void testGetFreeTrucksByStartCityAndCapacityInCargoListEmpty() {
        List<CargoDto> cargoDtoList = Collections.singletonList(cargoDto);
        List<Cargo> cargoList = Collections.singletonList(cargo);

        when(cargoConverter.toListEntity(cargoDtoList)).thenReturn(cargoList);
        when(truckDao.getAllFreeTrucksInCities(anySet(), anyInt())).thenReturn(Collections.EMPTY_LIST);

        assertEquals(truckService.getFreeTrucksByStartCityAndCapacityInCargoList(cargoDtoList).size(), 0);
    }

}
