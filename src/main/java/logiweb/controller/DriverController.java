package logiweb.controller;

import logiweb.dto.DriverDto;
import logiweb.dto.DriverEditDto;
import logiweb.dto.UserDto;
import logiweb.entity.enums.DriverStatus;
import logiweb.service.api.CityService;
import logiweb.service.api.DriverService;
import logiweb.service.api.UserService;
import logiweb.validator.DriverValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/officer/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverValidator driverValidator;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String allDrivers(Model model) {
        model.addAttribute("driverList", driverService.getAll());
        return "drivers/driverList";
    }

    @GetMapping("/{id}")
    public String driverInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("driver", driverService.getById(id));
        return "drivers/driverInfo";
    }

    @GetMapping("/enable/{id}")
    public String enableDriver(@PathVariable("id") int id, Model model) {
        driverService.enableDriver(id);


        return "redirect:/officer/drivers";
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
        DriverDto oldDriver = driverService.getById(driverEditDto.getId());
        driverEditDto.setTimeLastChangeStatus(oldDriver.getTimeLastChangeStatus());
        if (!Objects.equals(oldDriver.getPersonalNumber(), driverEditDto.getPersonalNumber()) &&
            !driverValidator.isValid(driverEditDto)) {
            return "drivers/notValid";
        }

        driverService.edit(driverEditDto);
        return "redirect:/officer/drivers";
    }

    @GetMapping("/add")
    public String addDriver(Model model) {
        List<UserDto> userDtoList = userService.getUsersWithRoleDriverWhoAreNotInListDrivers();

        if (userDtoList.isEmpty()) {
            return "drivers/notSuitableUsers";
        }

        model.addAttribute("cityList", cityService.getAll());
        model.addAttribute("users", userDtoList);
        model.addAttribute("driver", new DriverDto());
        return "drivers/addDriver";
    }

    @PostMapping("/add")
    public String addDriver(@ModelAttribute DriverDto driverDto,
                            @RequestParam("user-id") int userId) {
        if (!driverValidator.isValid(driverDto)) {
            return "drivers/notValid";
        }

        driverService.add(driverDto, userId);
        return "redirect:/officer/drivers";
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable("id") int id, Model model) {
        model.addAttribute("driver", driverService.getById(id));
        return "drivers/deleteDriver";
    }

    @PostMapping("/delete")
    public String deleteDriver(@ModelAttribute DriverEditDto driverEditDto) {
        driverService.disableDriver(driverEditDto);
        return "redirect:/officer/drivers";
    }
}
