package app.tests;

import logiweb.service.CityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static app.tests.DataInit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Before
    public void setUp() {
        DataInit.setUpCityDtoFrom();
        DataInit.setUpCityDtoTo();
    }

    @Test
    public void testGetCityByNameFromList() {
        assertEquals(
                cityService.getCityByNameFromList(Arrays.asList(cityDtoFrom, cityDtoTo), cityDtoFrom.getName()).getId(),
                cityDtoFrom.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetCityByNameFromListNull() {
        assertEquals(
                cityService.getCityByNameFromList(Arrays.asList(cityDtoFrom, cityDtoTo), "").getId(),
                cityDtoFrom.getId());
    }
}
