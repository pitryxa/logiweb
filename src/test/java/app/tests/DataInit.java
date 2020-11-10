package app.tests;

import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
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
    static CargoDto cargoDto;
    static CityDto cityDtoFrom;
    static CityDto cityDtoTo;
    static DistanceDto distanceDto;
    static DriverDto firstDriverDto;
    static DriverDto secondDriverDto;
    static SimpleDriverDto firstSimpleDriverDto;
    static SimpleDriverDto secondSimpleDriverDto;
    static OrderDto orderDto;
    static TruckDto truckDto;
    static SimpleTruckDto simpleTruckDto;
    static UserDto userDtoFirstDriver;
    static UserDto userDtoSecondDriver;
    static UserDto userDtoNew;
    static UserDto userDtoFreeDriver;
    static WaypointDto firstWaypointDto;
    static WaypointDto secondWaypointDto;
    static WaypointDto thirdWaypointDto;

    static Cargo cargo;
    static City cityFrom;
    static City cityTo;
    static Distance distance;
    static Driver firstDriver;
    static Driver secondDriver;
    static Order order;
    static Truck truck;
    static User userFirstDriver;
    static User userSecondDriver;
    static User userNew;
    static User userFreeDriver;
    static WaypointEntity firstWaypointEntity;
    static WaypointEntity secondWaypointEntity;
    static WaypointEntity thirdWaypointEntity;

    static DisplayRestDto displayRestDto;
    static DriverRestDto driverRestDto;
    static OrderRestDto orderRestDto;
    static TruckRestDto truckRestDto;

    static Route route;
    static Waypoint waypointFirst;
    static Waypoint waypointSecond;
    static Waypoint waypointThird;

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

        setUpFirstDriverDto();
        setUpFirstDriver();
        setUpFirstSimpleDriverDto();
        setUpSecondDriverDto();
        setUpSecondDriver();
        setUpSecondSimpleDriverDto();

        setUpTruckDto();
        setUpTruck();

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
        cargoDto = new CargoDto();
        cargoDto.setId(1000);
        cargoDto.setName("TestCargoName");
        cargoDto.setWeight(1234);
        cargoDto.setStatus(CargoStatus.PREPARED);
        cargoDto.setCityFrom("Moscow");
        cargoDto.setCityTo("Vologda");
        cargoDto.setOrderId(1000);
    }

    public static void setUpCargo() {
        cargo = new Cargo();
        cargo.setId(1000);
        cargo.setName("TestCargoName");
        cargo.setWeight(1234);
        cargo.setStatus(CargoStatus.PREPARED);
        cargo.setCityFrom(cityFrom);
        cargo.setCityTo(cityTo);
    }

    public static void setUpCityDtoFrom() {
        cityDtoFrom = new CityDto();
        cityDtoFrom.setId(1000);
        cityDtoFrom.setName("Moscow");
    }

    public static void setUpCityDtoTo() {
        cityDtoTo = new CityDto();
        cityDtoTo.setId(1001);
        cityDtoTo.setName("Vologda");
    }

    public static void setUpCityFrom() {
        cityFrom = new City();
        cityFrom.setId(1000);
        cityFrom.setName("Moscow");
    }

    public static void setUpCityTo() {
        cityTo = new City();
        cityTo.setId(1001);
        cityTo.setName("Vologda");
    }

    public static void setUpDistanceDto() {
        distanceDto = new DistanceDto();
        distanceDto.setId(1000);
        distanceDto.setCityFrom(cityDtoFrom.getName());
        distanceDto.setCityTo(cityDtoTo.getName());
        distanceDto.setDistance(470);
    }

    public static void setUpDistance() {
        distance = new Distance();
        distance.setId(1000);
        distance.setCityFrom(cityFrom);
        distance.setCityTo(cityTo);
        distance.setDistance(470);
    }

    public static void setUpFirstDriverDto() {
        firstDriverDto = new DriverDto();
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
        firstDriver = new Driver();
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
        firstSimpleDriverDto = new SimpleDriverDto();
        firstSimpleDriverDto.setId(1000);
        firstSimpleDriverDto.setPersonalNumber(123456L);
        firstSimpleDriverDto.setUser(userDtoFirstDriver);
        firstSimpleDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
    }

    public static void setUpSecondDriverDto() {
        secondDriverDto = new DriverDto();
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
        secondDriver = new Driver();
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
        secondSimpleDriverDto = new SimpleDriverDto();
        secondSimpleDriverDto.setId(1001);
        secondSimpleDriverDto.setPersonalNumber(654321L);
        secondSimpleDriverDto.setUser(userDtoSecondDriver);
        secondSimpleDriverDto.setStatus(DriverStatus.SECOND_DRIVER);
    }

    public static void setUpOrderDto() {
        orderDto = new OrderDto();
        orderDto.setId(1000);
        orderDto.setTruck(simpleTruckDto);
        orderDto.setDrivers(Arrays.asList(firstSimpleDriverDto, secondSimpleDriverDto));
        orderDto.setStatus(OrderStatus.PREPARED);
        orderDto.setWaypoints(Arrays.asList(firstWaypointDto, secondWaypointDto, thirdWaypointDto));
    }

    public static void setUpOrder() {
        order = new Order();
        order.setId(1000);
        order.setTruck(truck);
        order.setStatus(OrderStatus.PREPARED);
        order.setWaypointEntities(Arrays.asList(firstWaypointEntity, secondWaypointEntity, thirdWaypointEntity));
//        order.setWaypointEntities(new ArrayList<WaypointEntity>(Stream.of(firstWaypointEntity, secondWaypointEntity, thirdWaypointEntity).collect(
//                Collectors.toList())));
        order.setDistance(940);
    }

    public static void setUpTruckDto() {
        truckDto = new TruckDto();
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
        truck = new Truck();
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
        simpleTruckDto = new SimpleTruckDto();
        simpleTruckDto.setId(1000);
        simpleTruckDto.setRegNumber("AB12345");
    }

    public static void setUpUserDtoFirstDriver() {
        userDtoFirstDriver = new UserDto();
        userDtoFirstDriver.setId(1000);
        userDtoFirstDriver.setFirstName("Sergey");
        userDtoFirstDriver.setLastName("Esenin");
        userDtoFirstDriver.setRole(Role.ROLE_DRIVER);
        userDtoFirstDriver.setEmail("esenin@logiweb.org");
        userDtoFirstDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoFirstDriver.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoSecondDriver() {
        userDtoSecondDriver = new UserDto();
        userDtoSecondDriver.setId(1001);
        userDtoSecondDriver.setFirstName("Alexander");
        userDtoSecondDriver.setLastName("Block");
        userDtoSecondDriver.setRole(Role.ROLE_DRIVER);
        userDtoSecondDriver.setEmail("block@logiweb.org");
        userDtoSecondDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoSecondDriver.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserFirstDriver() {
        userFirstDriver = new User();
        userFirstDriver.setId(1000);
        userFirstDriver.setFirstName("Sergey");
        userFirstDriver.setLastName("Esenin");
        userFirstDriver.setRole(Role.ROLE_DRIVER);
        userFirstDriver.setEmail("esenin@logiweb.org");
        userFirstDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserSecondDriver() {
        userSecondDriver = new User();
        userSecondDriver.setId(1001);
        userSecondDriver.setFirstName("Alexander");
        userSecondDriver.setLastName("Block");
        userSecondDriver.setRole(Role.ROLE_DRIVER);
        userSecondDriver.setEmail("block@logiweb.org");
        userSecondDriver.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoNew() {
        userDtoNew = new UserDto();
        userDtoNew.setId(1002);
        userDtoNew.setFirstName("Ivan");
        userDtoNew.setLastName("Ivanov");
        userDtoNew.setRole(Role.ROLE_NONE);
        userDtoNew.setEmail("ivanov@logiweb.org");
        userDtoNew.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
        userDtoNew.setConfirmPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserNew() {
        userNew = new User();
        userNew.setId(1002);
        userNew.setFirstName("Ivan");
        userNew.setLastName("Ivanov");
        userNew.setRole(Role.ROLE_NONE);
        userNew.setEmail("ivanov@logiweb.org");
        userNew.setPassword("$2a$11$R84XGKkSg4SUMI8dbTCdUOsABeIGqrt2i5mbSbY8nw1dzBuWBbFAK");
    }

    public static void setUpUserDtoFreeDriver() {
        userDtoFreeDriver = new UserDto();
        userDtoFreeDriver.setId(1003);
        userDtoFreeDriver.setRole(Role.ROLE_DRIVER);
    }

    public static void setUpUserFreeDriver() {
        userFreeDriver = new User();
        userFreeDriver.setId(1003);
        userFreeDriver.setRole(Role.ROLE_DRIVER);
    }

    public static void setUpFirstWaypointDto() {
        firstWaypointDto = new WaypointDto();
        firstWaypointDto.setId(1000);
        firstWaypointDto.setOrderId(1000);
        firstWaypointDto.setCity("Moscow");
        firstWaypointDto.setCargo(cargoDto);
        firstWaypointDto.setOperation(OperationTypeOnWaypoint.LOAD);
        firstWaypointDto.setSequentialNumber(1);
        firstWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpSecondWaypointDto() {
        secondWaypointDto = new WaypointDto();
        secondWaypointDto.setId(1001);
        secondWaypointDto.setOrderId(1000);
        secondWaypointDto.setCity("Vologda");
        secondWaypointDto.setCargo(cargoDto);
        secondWaypointDto.setOperation(OperationTypeOnWaypoint.UNLOAD);
        secondWaypointDto.setSequentialNumber(2);
        secondWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpThirdWaypointDto() {
        thirdWaypointDto = new WaypointDto();
        thirdWaypointDto.setId(1002);
        thirdWaypointDto.setOrderId(1000);
        thirdWaypointDto.setCity("Moscow");
        thirdWaypointDto.setCargo(null);
        thirdWaypointDto.setOperation(OperationTypeOnWaypoint.NONE);
        thirdWaypointDto.setSequentialNumber(3);
        thirdWaypointDto.setStatus(WaypointStatus.UNDONE);
    }

    public static void setUpFirstWaypoint() {
        firstWaypointEntity = new WaypointEntity();
        firstWaypointEntity.setId(1000);
        firstWaypointEntity.setOrder(order);
        firstWaypointEntity.setCity(cityFrom);
        firstWaypointEntity.setCargo(cargo);
        firstWaypointEntity.setOperation(OperationTypeOnWaypoint.LOAD);
        firstWaypointEntity.setStatus(WaypointStatus.UNDONE);
        firstWaypointEntity.setSequentialNumber(1);
    }

    public static void setUpSecondWaypoint() {
        secondWaypointEntity = new WaypointEntity();
        secondWaypointEntity.setId(1001);
        secondWaypointEntity.setOrder(order);
        secondWaypointEntity.setCity(cityTo);
        secondWaypointEntity.setCargo(cargo);
        secondWaypointEntity.setOperation(OperationTypeOnWaypoint.UNLOAD);
        secondWaypointEntity.setStatus(WaypointStatus.UNDONE);
        secondWaypointEntity.setSequentialNumber(2);
    }

    public static void setUpThirdWaypoint() {
        thirdWaypointEntity = new WaypointEntity();
        thirdWaypointEntity.setId(1002);
        thirdWaypointEntity.setOrder(order);
        thirdWaypointEntity.setCity(cityFrom);
        thirdWaypointEntity.setCargo(null);
        thirdWaypointEntity.setOperation(OperationTypeOnWaypoint.NONE);
        thirdWaypointEntity.setStatus(WaypointStatus.UNDONE);
        thirdWaypointEntity.setSequentialNumber(3);
    }

    public static void setUpDisplayRestDto() {
        displayRestDto = new DisplayRestDto();
        displayRestDto.setOrders(Arrays.asList(orderRestDto));
        displayRestDto.setDrivers(driverRestDto);
        displayRestDto.setTrucks(truckRestDto);
    }

    public static void setUpDriverRestDto() {
        driverRestDto = new DriverRestDto();
        driverRestDto.setAll(2);
        driverRestDto.setFree(0);
    }

    public static void setUpTruckRestDto() {
        truckRestDto = new TruckRestDto();
        truckRestDto.setAll(1);
        truckRestDto.setBroken(0);
        truckRestDto.setFree(0);
    }

    public static void setUpRoute() {
        route = new Route();
        route.setDistance(940);
        route.setWaypoints(new LinkedList<>(Arrays.asList(waypointFirst, waypointSecond, waypointThird)));
    }

    public static void setUpWaypointFirst() {
        Map<CargoDto, OperationTypeOnWaypoint> cargoes = new LinkedHashMap<>();
        cargoes.put(cargoDto, OperationTypeOnWaypoint.LOAD);

        waypointFirst = new Waypoint();
        waypointFirst.setCity(cityDtoFrom);
        waypointFirst.setCargoes(cargoes);
        waypointFirst.setSumWeight(1234);
        waypointFirst.setDistanceFromPrevWaypoint(0);
    }

    public static void setUpWaypointSecond() {
        Map<CargoDto, OperationTypeOnWaypoint> cargoes = new LinkedHashMap<>();
        cargoes.put(cargoDto, OperationTypeOnWaypoint.UNLOAD);

        waypointSecond = new Waypoint();
        waypointSecond.setCity(cityDtoTo);
        waypointSecond.setCargoes(cargoes);
        waypointSecond.setSumWeight(0);
        waypointSecond.setDistanceFromPrevWaypoint(470);
    }

    public static void setUpWaypointThird() {
        waypointThird = new Waypoint();
        waypointThird.setCity(cityDtoFrom);
        waypointThird.setCargoes(null);
        waypointThird.setSumWeight(0);
        waypointThird.setDistanceFromPrevWaypoint(470);
    }
}
