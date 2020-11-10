package logiweb.converter;

import logiweb.dao.api.CityDao;
import logiweb.dao.api.TruckDao;
import logiweb.dao.api.UserDao;
import logiweb.dto.DriverDto;
import logiweb.dto.DriverEditDto;
import logiweb.dto.UserDto;
import logiweb.dto.simple.SimpleDriverDto;
import logiweb.entity.Driver;
import logiweb.entity.User;
import logiweb.service.api.CityService;
import logiweb.service.api.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverConverter {
    @Autowired
    private CityService cityService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    public DriverDto toDto(Driver driver) {
        DriverDto driverDto = new DriverDto();

        driverDto.setId(driver.getId());
        driverDto.setPersonalNumber(driver.getPersonalNumber());
        driverDto.setUser(mapper.map(driver.getUser(), UserDto.class));
        driverDto.setWorkHours(driver.getWorkHours());
        driverDto.setStatus(driver.getStatus());

        driverDto.setTruck(
                driver.getTruck() == null
                        ? null
                        : truckConverter.toSimpleDto(driver.getTruck())
        );

        driverDto.setCity(driver.getCity().getName());

        driverDto.setTimeLastChangeStatus(driver.getTimeLastChangeStatus());

        driverDto.setOrderId(driverService.getOrderId(driver));

        return driverDto;
    }

    public SimpleDriverDto toSimpleDto(Driver driver) {
        SimpleDriverDto driverDto = new SimpleDriverDto();

        driverDto.setId(driver.getId());
        driverDto.setPersonalNumber(driver.getPersonalNumber());
        driverDto.setUser(mapper.map(driver.getUser(), UserDto.class));
        driverDto.setStatus(driver.getStatus());

        return driverDto;
    }

    public Driver toEntity(DriverDto driverDto) {
        Driver driver = new Driver();

        driver.setId(driverDto.getId());
        driver.setPersonalNumber(driverDto.getPersonalNumber());
        driver.setUser(mapper.map(driverDto.getUser(), User.class));
        driver.setWorkHours(driverDto.getWorkHours());
        driver.setStatus(driverDto.getStatus());

        driver.setTruck(
                driverDto.getTruck() == null
                        ? null
                        : truckConverter.toEntity(driverDto.getTruck())
        );

        driver.setCity(cityConverter.toEntity(cityService.getByName(driverDto.getCity())));
        driver.setTimeLastChangeStatus(driverDto.getTimeLastChangeStatus());

        return driver;
    }

    public Driver toEntity(DriverEditDto driverEditDto) {
        Driver driver = new Driver();

        driver.setId(driverEditDto.getId());
        driver.setPersonalNumber(driverEditDto.getPersonalNumber());
        driver.setUser(userDao.getById(driverEditDto.getUserId()));
        driver.setWorkHours(driverEditDto.getWorkHours());
        driver.setStatus(driverEditDto.getStatus());

        Integer truckId = driverEditDto.getTruckId();
        driver.setTruck(truckId == null ? null : truckDao.getById(truckId));

        driver.setCity(cityDao.getByName(driverEditDto.getCity()));
        driver.setTimeLastChangeStatus(driverEditDto.getTimeLastChangeStatus());

        return driver;
    }

    public List<DriverDto> toListDto(List<Driver> drivers) {
        return drivers
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<SimpleDriverDto> toListSimpleDto(List<Driver> drivers) {
        return drivers
                .stream()
                .map(this::toSimpleDto)
                .collect(Collectors.toList());
    }

    public List<Driver> toListEntity(List<SimpleDriverDto> simpleDriverDtoList) {
        return simpleDriverDtoList
                .stream()
                .map(driver -> toEntity(driverService.getById(driver.getId())))
                .collect(Collectors.toList());
    }

    public List<Driver> toListEntityFromDto(List<DriverDto> drivers) {
        return drivers
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
