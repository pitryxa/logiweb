package logiweb.controller;

import logiweb.dto.DriverDto;
import logiweb.dto.DriverEditDto;
import logiweb.dto.UserDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.Role;
import logiweb.service.api.CityService;
import logiweb.service.api.DriverService;
import logiweb.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/officer/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String allDrivers(Model model) {
        model.addAttribute("driverList", driverService.getAllNotDisabled());
        return "drivers/driverList";
    }

    @GetMapping("/{id}")
    public String driverInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("driver", driverService.getById(id));
        return "drivers/driverInfo";
    }

    @GetMapping("/edit/{id}")
    public String editDriver(@PathVariable("id") int id, Model model) {
        model.addAttribute("driver", driverService.getById(id));
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("statuses", DriverStatus.values());
        return "drivers/editDriver";
    }

    @PostMapping("/edit")
    public String editDriver(@ModelAttribute DriverEditDto driverEditDto) {
        driverEditDto.setTimeLastChangeStatus(driverService.getById(driverEditDto.getId()).getTimeLastChangeStatus());

        driverService.edit(driverEditDto);
        return "redirect:/officer/drivers";
    }

    @GetMapping("/add")
    public String addDriver(Model model) {
        model.addAttribute("cityList", cityService.getAll());
        model.addAttribute("users", userService.getUsersWithRoleDriverWhoAreNotInListDrivers());
        return "drivers/addDriver";
    }

    @PostMapping("/add")
    public String addDriver(@ModelAttribute DriverDto driverDto,
                            @RequestParam("user-id") int userId) {
        driverDto.setUser(userService.getById(userId));
        driverDto.setWorkHours(0.0);
        driverDto.setStatus(DriverStatus.RECREATION);
        driverDto.setTruck(null);
        driverDto.setTimeLastChangeStatus(LocalDateTime.now());

        driverService.add(driverDto);
        return "redirect:/officer/drivers";
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable("id") int id, Model model) {
        model.addAttribute("driver", driverService.getById(id));
        return "drivers/deleteDriver";
    }

    @PostMapping("/delete")
    public String deleteDriver(@ModelAttribute DriverEditDto driverEditDto) {
//        driverDto.setUser(userService.getById(userId));
//        driverDto.setTruck(null);
        driverEditDto.setTimeLastChangeStatus(LocalDateTime.now());
        driverEditDto.setStatus(DriverStatus.DISABLED);

        UserDto userDto = userService.getById(driverEditDto.getUserId());
        userDto.setRole(Role.ROLE_NONE);
        userService.edit(userDto);

        driverService.edit(driverEditDto);
        return "redirect:/officer/drivers";
    }
}
