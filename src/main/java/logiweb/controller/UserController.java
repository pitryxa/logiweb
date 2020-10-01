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
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", Role.values());
        return "users/editUser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserDto userDto) {
        userService.edit(userDto);
        return "redirect:/admin/users";
    }
}
