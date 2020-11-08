package logiweb.controller;

import logiweb.dto.DriverDto;
import logiweb.dto.OrderDto;
import logiweb.dto.WaypointDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.WaypointStatus;
import logiweb.service.api.DriverService;
import logiweb.service.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverPersonalController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private WaypointService waypointService;

    @GetMapping
    public String getDriverPersonalCard(Model model) {
        DriverDto currentDriver = driverService.getCurrentDriver();
        model.addAttribute("currentDriver", currentDriver);
        return "driverPersonal/personalCard";
    }

    @GetMapping("/order")
    public String getOrderPage(Model model) {
        DriverDto currentDriver = driverService.getCurrentDriver();
        model.addAttribute("currentDriver", currentDriver);

        if (currentDriver.getTruck() == null) {
            return "driverPersonal/notAssignedOrder";
        }

        OrderDto orderDto = driverService.getOrderByDriver(currentDriver);

        if (orderDto == null) {
            throw new RuntimeException("Not found assigned order!");
        }
        WaypointDto currentWaypoint = waypointService.getCurrentWaypointFromOrder(orderDto);
        if (currentWaypoint == null) {
            throw new RuntimeException("Not found assigned order!");
        }
        model.addAttribute("order", orderDto);
        model.addAttribute("currentWaypoint", currentWaypoint);

        return "driverPersonal/orderPage";
    }

    @GetMapping("/start-order")
    public String getOrder(Model model) {
        return "driverPersonal/startExecuteOrder";
    }

    @PostMapping("/start-order")
    public String getOrder() {
        driverService.startExecuteOrder();
        return "redirect:/driver/order";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam("status") DriverStatus status) {
        driverService.changeDriversStatusesInOrder(status);
        return "redirect:/driver/order";
    }

    @GetMapping("/done-waypoint/{id}")
    public String doneWaypointGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("waypoint", waypointService.getById(id));
        return "driverPersonal/confirmDoneWaypoint";
    }

    @PostMapping("/done-waypoint")
    public String doneWaypointPost(@RequestParam("id") int id, @RequestParam("orderId") int orderId, Model model) {
        waypointService.doneWaypoint(id, orderId);
        return "redirect:/driver/order";
    }


}
