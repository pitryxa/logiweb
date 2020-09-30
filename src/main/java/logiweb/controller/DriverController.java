package logiweb.controller;

import logiweb.converter.TruckConverter;
import logiweb.dto.CargoDto;
import logiweb.dto.DriverDto;
import logiweb.dto.TruckDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.Role;
import logiweb.entity.enums.TruckStatus;
import logiweb.service.api.CityService;
import logiweb.service.api.DriverService;
import logiweb.service.api.TruckService;
import logiweb.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/officer/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckConverter truckConverter;

    @GetMapping
    public ModelAndView allDrivers() {
        ModelAndView model = new ModelAndView();
        model.setViewName("drivers/driverList");
        model.addObject("driverList", driverService.getAll());
        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView driverInfo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView("drivers/driverInfo");
        model.addObject("driver", driverService.getById(id));
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editDriver(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("drivers/editDriver");
        model.addObject("driver", driverService.getById(id));
        model.addObject("cities", cityService.getAll());
        model.addObject("statuses", DriverStatus.values());
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editDriver(@ModelAttribute DriverDto driverDto) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/officer/drivers");
        driverService.edit(driverDto);
        return model;
    }

    @GetMapping("/add")
    public ModelAndView addDriver() {
        ModelAndView model = new ModelAndView();
        model.setViewName("drivers/addDriver");
        model.addObject("cityList", cityService.getAll());
//        model.addObject("statusArray", DriverStatus.values());
        model.addObject("users", userService.getByRole(Role.ROLE_DRIVER));
        return model;
    }

    @PostMapping("/add")
    public ModelAndView addDriver(@ModelAttribute DriverDto driverDto,
                                  @RequestParam("user-id") int userId ) {
        ModelAndView model = new ModelAndView("redirect:/officer/drivers");

        driverDto.setUser(userService.getById(userId));
        driverDto.setWorkHours(0);
        driverDto.setStatus(DriverStatus.RECREATION);
        driverDto.setTruck(null);

        driverService.add(driverDto);
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteDriver(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("drivers/deleteDriver");
        model.addObject("driver", driverService.getById(id));
        return model;
    }

    @PostMapping("/delete")
    public ModelAndView deleteDriver(@ModelAttribute DriverDto driverDto,
                                     @RequestParam("user-id") Integer userId) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/officer/drivers");

        driverDto.setUser(userService.getById(userId));
        driverDto.setTruck(null);

        driverService.delete(driverDto);
        return model;
    }
}
