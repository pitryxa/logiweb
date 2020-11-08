package logiweb.controller;

import logiweb.calculating.Route;
import logiweb.calculating.RoutesCalc;
import logiweb.dto.CargoDto;
import logiweb.dto.DriverDto;
import logiweb.dto.OrderDto;
import logiweb.dto.TruckDto;
import logiweb.entity.Order;
import logiweb.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/officer/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RoutesCalc routesCalc;

    @GetMapping
    public String allOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "orders/orderList";
    }

    @GetMapping("/add-cargo")
    public String addCargoToOrder(Model model) {
        List<CargoDto> cargoDtoList = cargoService.getPreparedCargo();
        if (cargoDtoList == null) {
            return "orders/noCargoes";
        }

        model.addAttribute("cargoes", cargoDtoList);
        return "orders/addCargoToOrder";
    }

    @PostMapping("/add-cargo")
    public String addCargoToOrder(@RequestParam("cargoes") List<Integer> cargoIds, HttpSession session) {
        List<CargoDto> cargoes = cargoService.getByListId(cargoIds);

        session.setAttribute("cargoListForOrder", cargoes);

        return "redirect:/officer/orders/add-truck";
    }

    @GetMapping("/add-truck")
    public String addTruckToOrder(Model model, HttpSession session) {
        Object o = session.getAttribute("cargoListForOrder");
        if (o == null) {
            return "redirect:/officer/orders";
        }
        List<CargoDto> cargoes = (List<CargoDto>) o;

        List<TruckDto> trucks = truckService.getFreeTrucksByStartCityAndCapacityInCargoList(cargoes);

        if (trucks.isEmpty()) {
            return "orders/noSuitableTrucks";
        }

        model.addAttribute("cargoes", cargoes);
        model.addAttribute("trucks", trucks);

        return "orders/addTruckToOrder";
    }

    @PostMapping("/add-truck")
    public String addTruckToOrder(@RequestParam("truck") int truckId, HttpSession session) {
        TruckDto truck = truckService.getById(truckId);

        session.setAttribute("truckForOrder", truck);

        List<CargoDto> cargoes = ((List<CargoDto>) session.getAttribute("cargoListForOrder"));
        Route route = routesCalc.minRouteByCargoes(cargoes, truck);
        if (route == null) {
            return "redirect:/officer/orders";
        }
        session.setAttribute("routeForOrder", route);

        return "redirect:/officer/orders/add-drivers";
    }

    @GetMapping("/add-drivers")
    public String addDriversToOrder(Model model, HttpSession session) {
        Object cargoList = session.getAttribute("cargoListForOrder");
        Object truckForOrder = session.getAttribute("truckForOrder");
        Object routeForOrder = session.getAttribute("routeForOrder");
        if (cargoList == null || truckForOrder == null || routeForOrder == null) {
            return "redirect:/officer/orders";
        }
        List<CargoDto> cargoes = (List<CargoDto>) cargoList;
        TruckDto truckDto = (TruckDto) truckForOrder;
        Route route = (Route) routeForOrder;

        List<DriverDto> drivers = driverService.getDriversForOrder(truckDto, route);

        if (driverService.isNotEnoughDrivers(drivers, truckDto.getShiftSize())) {
            return "orders/notEnoughDrivers";
        }

        session.setAttribute("suitableDriversForOrder", drivers);

        model.addAttribute("cargoes", cargoes);
        model.addAttribute("truck", truckDto);
        model.addAttribute("route", route);
        model.addAttribute("drivers", drivers);

        return "orders/addDriversToOrder";
    }

    @PostMapping("/add-drivers")
    public String addDriversToOrder(@RequestParam("drivers") List<Integer> driversIds, HttpSession session,
                                    Model model) {
        Object cargoList = session.getAttribute("cargoListForOrder");
        Object truckForOrder = session.getAttribute("truckForOrder");
        Object routeForOrder = session.getAttribute("routeForOrder");
        Object suitableDrivers = session.getAttribute("suitableDriversForOrder");
        if (cargoList == null || truckForOrder == null || routeForOrder == null || suitableDrivers == null) {
            return "redirect:/officer/orders";
        }
        List<CargoDto> cargoes = (List<CargoDto>) cargoList;
        Route route = (Route) routeForOrder;
        TruckDto truck = (TruckDto) truckForOrder;
        List<DriverDto> selectedDrivers = driverService.getByIdFromList((List<DriverDto>) suitableDrivers, driversIds);

        if (driverService.isWrongAmountDrivers(selectedDrivers, truck.getShiftSize())) {
            model.addAttribute("shiftSize", truck.getShiftSize());
            return "orders/wrongAmountDrivers";
        }

        orderService.add(cargoes, truck, route, selectedDrivers);


        return "redirect:/officer/orders";
    }

    @GetMapping("/{id}")
    public String orderInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.getById(id));
        return "orders/orderInfo";
    }

}
