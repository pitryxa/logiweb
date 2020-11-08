package logiweb.controller;

import logiweb.dto.UserDto;
import logiweb.entity.enums.Role;
import logiweb.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("usersList", userService.getAll());
        return "users/usersList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        UserDto userDto = userService.getById(id);

        model.addAttribute("user", userDto);
        model.addAttribute("roles", Role.values());
        model.addAttribute("isRoleUnselectable", userService.isUserBusyDriver(userDto));
        return "users/editUser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserDto userDto, @RequestParam("currentRole") Role currentRole) {
        userService.edit(userDto, currentRole);
        return "redirect:/admin/users";
    }
}
