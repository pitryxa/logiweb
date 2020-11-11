package app.tests;

import logiweb.service.calculating.Route;
import logiweb.service.calculating.Waypoint;
import logiweb.dto.*;
import logiweb.dto.rest.DisplayRestDto;
import logiweb.dto.rest.DriverRestDto;
import logiweb.dto.rest.OrderRestDto;
import logiweb.dto.rest.TruckRestDto;
import logiweb.dto.simple.SimpleDriverDto;
import logiweb.dto.simple.SimpleTruckDto;
import logiweb.entity.*;
import logiweb.entity.enums.*;

import java.time.LocalDateTime;
import java.util.*;

public class DataInit {
    static CargoDto cargoDto = new CargoDto();
    static CityDto cityDtoFrom = new CityDto();
    static CityDto cityDtoTo = new CityDto();
    static DistanceDto distanceDto = new DistanceDto();
    static DriverDto firstDriverDto = new DriverDto();
    static DriverDto secondDriverDto = new DriverDto();
    static SimpleDriverDto firstSimpleDriverDto = new SimpleDriverDto();
    static SimpleDriverDto secondSimpleDriverDto = new SimpleDriverDto();
    static OrderDto orderDto = new OrderDto();
    static TruckDto truckDto = new TruckDto();
    static SimpleTruckDto simpleTruckDto = new SimpleTruckDto();
    static UserDto userDtoFirstDriver = new UserDto();
    static UserDto userDtoSecondDriver = new UserDto();
    static UserDto userDtoNew = new UserDto();
    static UserDto userDtoFreeDriver = new UserDto();
    static WaypointDto firstWaypointDto = new WaypointDto();
    static WaypointDto secondWaypointDto = new WaypointDto();
    static WaypointDto thirdWaypointDto = new WaypointDto();

    static Cargo cargo = new Cargo();
    static City cityFrom = new City();
    static City cityTo = new City();
    static Distance distance = new Distance();
    static Driver firstDriver = new Driver();
    static Driver secondDriver = new Driver();
    static Order order = new Order();
    static Truck truck = new Truck();
    static User userFirstDriver = new User();
    static User userSecondDriver = new User();
    static User userNew = new User();
    static User userFreeDriver = new User();
    static WaypointEntity firstWaypointEntity = new WaypointEntity();
    static WaypointEntity secondWaypointEntity = new WaypointEntity();
    static WaypointEntity thirdWaypointEntity = new WaypointEntity();

    static DisplayRestDto displayRestDto = new DisplayRestDto();
    static DriverRestDto driverRestDto = new DriverRestDto();
    static OrderRestDto orderRestDto = new OrderRestDto();
    static TruckRestDto truckRestDto = new TruckRestDto();

    static Route route = new Route();
    static Waypoint waypointFirst = new Waypoint();
    static Waypoint waypointSecond = new Waypoint();
    static Waypoint waypointThird = new Waypoint();

    public static void setUpAll() {
        setUpCityDtoFrom();
        setUpCityDtoTo();
        setUpCityFrom();
        setUpCityTo();

        setUpCargoDto();
        setUpCargo();

        setUpDistanceDto();
        setUpDistance();

        setUpUserDtoFirstDriver();
        setUpUserDtoSecondDriver();
        setUpUserFirstDriver();
        setUpUserSecondDriver();
        setUpUserDtoNew();
        setUpUserNew();
        setUpUserDtoFreeDriver();
        setUpUserFreeDriver();

        setUpSimpleTruckDto();
        setUpTruckDto();
        setUpTruck();

        setUpFirstDriverDto();
        setUpFirstDriver();
        setUpFirstSimpleDriverDto();
        setUpSecondDriverDto();
        setUpSecondDriver();
        setUpSecondSimpleDriverDto();

        setUpOrder();
        setUpOrderDto();

        setUpFirstWaypointDto();
        setUpSecondWaypointDto();
        setUpThirdWaypointDto();
        setUpFirstWaypoint();
        setUpSecondWaypoint();
        setUpThirdWaypoint();

        setUpDisplayRestDto();
        setUpDriverRestDto();
        setUpTruckRestDto();

        setUpRoute();
        setUpWaypointFirst();
        setUpWaypointSecond();
        setUpWaypointThird();
    }

    public static void setUpCargoDto() {
        cargoDto.setId(1000);
        cargoDto.setName("TestCargoName");
        cargoDto.setWeight(1234);
        cargoDto.setStatus(CargoStatus.PREPARED);
        cargoDto.setCityFrom("Moscow");
        cargoDto.setCityTo("Vologda");
        cargoDto.setOrderId(1000);
    }

    public static void setUpCargo() {
        cargo.setId(1000);
        cargo.setName("TestCargoName");
        cargo.setWeight(1234);
        cargo.setStatus(CargoStatus.PREPARED);
        cargo.setCityFrom(cityFrom);
        cargo.setCityTo(cityTo);
    }

    public static void setUpCityDtoFrom() {
        cityDtoFrom.setId(1000);
        cityDtoFrom.setName("Moscow");
    }

    public static void setUpCityDtoTo() {
        cityDtoTo.setId(1001);
        cityDtoTo.setName("Vologda");
    }

    public static void setUpCityFrom() {
        cityFrom.setId(1000);
        cityFrom.setName("Moscow");
    }

    public static void setUpCityTo() {
        cityTo.setId(1001);
        cityTo.setName("Vologda");
    }

    public static void setUpDistanceDto() {
        distanceDto.setId(1000);
        distanceDto.setCityFrom(cityDtoFrom.getName());
        distanceDto.setCityTo(cityDtoTo.getName());
        distanceDto.setDistance(470);
    }

    public static void setUpDistance() {
        distance.setId(1000);
        distance.setCityFrom(cityFrom);
        distance.setCityTo(cityTo);
        distance.setDistance(470);
    }

    public static void setUpFirstDriverDto() {
        firstDriverDto.setId(1000);
        firstDriverDto.setPersonalNumber(123456L);
        firstDriverDto.setUser(userDtoFirstDriver);
        firstDriverDto.setWorkHours(100.0);
        firstDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
        firstDriverDto.setTruck(simpleTruckDto);
        firstDriverDto.setCity("Moscow");
        firstDriverDto.setTimeLastChangeStatus(LocalDateTime.of(2020, 11, 9, 12, 10));
        firstDriverDto.setOrderId(1000);
    }

    public static void setUpFirstDriver() {
        firstDriver.setId(1000);
        firstDriver.setPersonalNumber(123456L);
        firstDriver.setUser(userFirstDriver);
        firstDriver.setWorkHours(100.0);
        firstDriver.setStatus(DriverStatus.SECOND_DRIVER);
        firstDriver.setTruck(truck);
        firstDriver.setCity(cityFrom);
        firstDriver.setTimeLastChangeStatus(LocalDateTime.of(2020, 11, 9, 12, 10));
    }

    public static void setUpFirstSimpleDriverDto() {
        firstSimpleDriverDto.setId(1000);
        firstSimpleDriverDto.setPersonalNumber(123456L);
        firstSimpleDriverDto.setUser(userDtoFirstDriver);
        firstSimpleDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
    }

    public static void setUpSecondDriverDto() {
        secondDriverDto.setId(1001);
        secondDriverDto.setPersonalNumber(654321L);
        secondDriverDto.setUser(userDtoSecondDriver);
        secondDriverDto.setWorkHours(100.0);
        secondDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
        secondDriverDto.setTruck(simpleTruckDto);
        secondDriverDto.setCity("Moscow");
        secondDriverDto.setTimeLastChangeStatus(LocalDateTime.of(2020, 11, 9, 12, 10));
        secondDriverDto.setOrderId(1000);
    }

    public static void setUpSecondDriver() {
        secondDriver.setId(1001);
        secondDriver.setPersonalNumber(654321L);
        secondDriver.setUser(userSecondDriver);
        secondDriver.setWorkHours(100.0);
        secondDriver.setStatus(DriverStatus.SECOND_DRIVER);
        secondDriver.setTruck(truck);
        secondDriver.setCity(cityFrom);
        secondDriver.setTimeLastChangeStatus(LocalDateTime.of(2020, 11, 9, 12, 10));
    }

    public static void setUpSecondSimpleDriverDto() {
        secondSimpleDriverDto.setId(1001);
        secondSimpleDriverDto.setPersonalNumber(654321L);
        secondSimpleDriverDto.setUser(userDtoSecondDriver);
        secondSimpleDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
    }

    public static void setUpOrderDto() {
        orderDto.setId(1000);
        orderDto.setTruck(simpleTruckDto);
        orderDto.setDrivers(Arrays.asList(firstSimpleDriverDto, secondSimpleDriverDto));
        orderDto.setStatus(OrderStatus.PREPARED);
        orderDto.setWaypoints(Arrays.asList(firstWaypointDto, secondWaypointDto, thirdWaypointDto));
    }

    public static void setUpOrder() {
        order.setId(1000);
        order.setTruck(truck);
        order.setStatus(OrderStatus.PREPARED);
        order.setWaypointEntities(Arrays.asList(firstWaypointEntity, secondWaypointEntity, thirdWaypointEntity));
        order.setDistance(940);
    }

    public static void setUpTruckDto() {
        truckDto.setId(1000);
        truckDto.setRegNumber("AB12345");
        truckDto.setShiftSize(2);
        truckDto.setCapacity(10);
        truckDto.setConditionStatus(TruckConditionStatus.OK);
        truckDto.setWorkStatus(TruckWorkStatus.IN_WAY);
        truckDto.setCity("Moscow");
        truckDto.setDrivers(Arrays.asList(firstSimpleDriverDto, secondSimpleDriverDto));
        truckDto.setOrderId(1000);
    }

    public static void setUpTruck() {
        truck.setId(1000);
        truck.setRegNumber("AB12345");
        truck.setShiftSize(2);
        truck.setCapacity(10);
        truck.setConditionStatus(TruckConditionStatus.OK);
        truck.setWorkStatus(TruckWorkStatus.IN_WAY);
        truck.setCity(cityFrom);
        truck.setDrivers(Arrays.asList(firstDriver, secondDriver));
    }

    public static void setUpSimpleTruckDto() {
        simpleTruckDto.setId(1000);
        simpleTruckDto.setRegNumber("AB12345");
    }

    public static void setUpUserDtoFirstDriver() {
        userDtoFirstDriver.setId(1000);
        userDtoFirstDriver.setFirstName("Sergey");
        userDtoFirstDriver.setLastName("Esenin");
        userDtoFirstDriver.setRole(Role.ROLE_DRIVER);
        userDtoFirstDriver.setEmail("esenin@logiweb.org");
        userDtoFirstDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoFirstDriver.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoSecondDriver() {
        userDtoSecondDriver.setId(1001);
        userDtoSecondDriver.setFirstName("Alexander");
        userDtoSecondDriver.setLastName("Block");
        userDtoSecondDriver.setRole(Role.ROLE_DRIVER);
        userDtoSecondDriver.setEmail("block@logiweb.org");
        userDtoSecondDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoSecondDriver.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserFirstDriver() {
        userFirstDriver.setId(1000);
        userFirstDriver.setFirstName("Sergey");
        userFirstDriver.setLastName("Esenin");
        userFirstDriver.setRole(Role.ROLE_DRIVER);
        userFirstDriver.setEmail("esenin@logiweb.org");
        userFirstDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserSecondDriver() {
        userSecondDriver.setId(1001);
        userSecondDriver.setFirstName("Alexander");
        userSecondDriver.setLastName("Block");
        userSecondDriver.setRole(Role.ROLE_DRIVER);
        userSecondDriver.setEmail("block@logiweb.org");
        userSecondDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoNew() {
        userDtoNew.setId(1002);
        userDtoNew.setFirstName("Ivan");
        userDtoNew.setLastName("Ivanov");
        userDtoNew.setRole(Role.ROLE_NONE);
        userDtoNew.setEmail("ivanov@logiweb.org");
        userDtoNew.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoNew.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserNew() {
        userNew.setId(1002);
        userNew.setFirstName("Ivan");
        userNew.setLastName("Ivanov");
        userNew.setRole(Role.ROLE_NONE);
        userNew.setEmail("ivanov@logiweb.org");
        userNew.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoFreeDriver() {
        userDtoFreeDriver.setId(1003);
        userDtoFreeDriver.setRole(Role.ROLE_DRIVER);
    }

    public static void setUpUserFreeDriver() {
        userFreeDriver.setId(1003);
        userFreeDriver.setRole(Role.ROLE_DRIVER);
    }

    public static void setUpFirstWaypointDto() {
        firstWaypointDto.setId(1000);
        firstWaypointDto.setOrderId(1000);
        firstWaypointDto.setCity("Moscow");
        firstWaypointDto.setCargo(cargoDto);
        firstWaypointDto.setOperation(OperationTypeOnWaypoint.LOAD);
        firstWaypointDto.setSequentialNumber(1);
        firstWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpSecondWaypointDto() {
        secondWaypointDto.setId(1001);
        secondWaypointDto.setOrderId(1000);
        secondWaypointDto.setCity("Vologda");
        secondWaypointDto.setCargo(cargoDto);
        secondWaypointDto.setOperation(OperationTypeOnWaypoint.UNLOAD);
        secondWaypointDto.setSequentialNumber(2);
        secondWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpThirdWaypointDto() {
        thirdWaypointDto.setId(1002);
        thirdWaypointDto.setOrderId(1000);
        thirdWaypointDto.setCity("Moscow");
        thirdWaypointDto.setCargo(null);
        thirdWaypointDto.setOperation(OperationTypeOnWaypoint.NONE);
        thirdWaypointDto.setSequentialNumber(3);
        thirdWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpFirstWaypoint() {
        firstWaypointEntity.setId(1000);
        firstWaypointEntity.setOrder(order);
        firstWaypointEntity.setCity(cityFrom);
        firstWaypointEntity.setCargo(cargo);
        firstWaypointEntity.setOperation(OperationTypeOnWaypoint.LOAD);
        firstWaypointEntity.setStatus(WaypointStatus.UNDONE);
        firstWaypointEntity.setSequentialNumber(1);
    }

    public static void setUpSecondWaypoint() {
        secondWaypointEntity.setId(1001);
        secondWaypointEntity.setOrder(order);
        secondWaypointEntity.setCity(cityTo);
        secondWaypointEntity.setCargo(cargo);
        secondWaypointEntity.setOperation(OperationTypeOnWaypoint.UNLOAD);
        secondWaypointEntity.setStatus(WaypointStatus.UNDONE);
        secondWaypointEntity.setSequentialNumber(2);
    }

    public static void setUpThirdWaypoint() {
        thirdWaypointEntity.setId(1002);
        thirdWaypointEntity.setOrder(order);
        thirdWaypointEntity.setCity(cityFrom);
        thirdWaypointEntity.setCargo(null);
        thirdWaypointEntity.setOperation(OperationTypeOnWaypoint.NONE);
        thirdWaypointEntity.setStatus(WaypointStatus.UNDONE);
        thirdWaypointEntity.setSequentialNumber(3);
    }

    public static void setUpDisplayRestDto() {
        displayRestDto.setOrders(Arrays.asList(orderRestDto));
        displayRestDto.setDrivers(driverRestDto);
        displayRestDto.setTrucks(truckRestDto);
    }

    public static void setUpDriverRestDto() {
        driverRestDto.setAll(2);
        driverRestDto.setFree(0);
    }

    public static void setUpTruckRestDto() {
        truckRestDto.setAll(1);
        truckRestDto.setBroken(0);
        truckRestDto.setFree(0);
    }

    public static void setUpRoute() {
        route.setDistance(940);
        route.setWaypoints(new LinkedList<>(Arrays.asList(waypointFirst, waypointSecond, waypointThird)));
    }

    public static void setUpWaypointFirst() {
        Map<CargoDto, OperationTypeOnWaypoint> cargoes = new LinkedHashMap<>();
        cargoes.put(cargoDto, OperationTypeOnWaypoint.LOAD);

        waypointFirst.setCity(cityDtoFrom);
        waypointFirst.setCargoes(cargoes);
        waypointFirst.setSumWeight(1234);
        waypointFirst.setDistanceFromPrevWaypoint(0);
    }

    public static void setUpWaypointSecond() {
        Map<CargoDto, OperationTypeOnWaypoint> cargoes = new LinkedHashMap<>();
        cargoes.put(cargoDto, OperationTypeOnWaypoint.UNLOAD);

        waypointSecond.setCity(cityDtoTo);
        waypointSecond.setCargoes(cargoes);
        waypointSecond.setSumWeight(0);
        waypointSecond.setDistanceFromPrevWaypoint(470);
    }

    public static void setUpWaypointThird() {
        waypointThird.setCity(cityDtoFrom);
        waypointThird.setCargoes(null);
        waypointThird.setSumWeight(0);
        waypointThird.setDistanceFromPrevWaypoint(470);
    }
}
