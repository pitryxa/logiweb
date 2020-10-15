package logiweb.controller;

import logiweb.calculating.Route;
import logiweb.calculating.RoutesCalc;
import logiweb.dto.CargoDto;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("cargoes", cargoService.getPreparedCargo());
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

        model.addAttribute("cargoes", cargoes);
        model.addAttribute("trucks", truckService.getFreeTrucksByStartCityAndCapacityInCargoList(cargoes));

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

        session.setAttribute("driversForOrder", drivers);

        model.addAttribute("cargoes", cargoes);
        model.addAttribute("truck", truckDto);
        model.addAttribute("route", route);
        model.addAttribute("drivers", drivers);

        return "orders/addDriversToOrder";
    }

    @PostMapping("/add-drivers")
    public String addDriversToOrder(@RequestParam("drivers") List<Integer> driversIds, HttpSession session, Model model) {
        List<DriverDto> drivers = (List<DriverDto>) session.getAttribute("driversForOrder");
        TruckDto truck = (TruckDto) session.getAttribute("truckForOrder");
        drivers = driverService.getByIdFromList(drivers, driversIds);

        if (driverService.isWrongAmountDrivers(drivers, truck.getShiftSize())) {
            model.addAttribute("shiftSize", truck.getShiftSize());
            return "orders/wrongAmountDrivers";
        }

        return "redirect:/officer/orders";
    }

}
