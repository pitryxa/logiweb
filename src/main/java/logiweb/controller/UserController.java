package logiweb.controller;

import logiweb.dto.CargoDto;
import logiweb.dto.UserDto;
import logiweb.entity.enums.CargoStatus;
import logiweb.entity.enums.Role;
import logiweb.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView allUsers() {
        ModelAndView model = new ModelAndView("users/usersList");
        model.addObject("usersList", userService.getAll());
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("users/editUser");
        model.addObject("user", userService.getById(id));
//        model.addObject("cityList", cityService.getAll());
        model.addObject("roles", Role.values());
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editUser(@ModelAttribute UserDto userDto) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/admin/users");
        userService.edit(userDto);
        return model;
    }
}
