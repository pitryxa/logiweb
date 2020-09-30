package logiweb.controller;

import logiweb.dto.UserDto;
import logiweb.service.api.UserService;
import logiweb.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/registration")
    public ModelAndView getRegistration() {
        return new ModelAndView("registration")
                .addObject("userForm", new UserDto());
    }

    /*@PostMapping(value = "/registration")
    public ModelAndView registration(@Valid @ModelAttribute UserDto userForm, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();

        //userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.setViewName("registration");
            model.addObject("userForm", userForm);
//            model.addObject("error", bindingResult);
        } else {
            userService.add(userForm);
            model.setViewName("redirect:/login");
        }

        return model;
    }*/

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userForm") @Valid UserDto userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.add(userForm);
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public ModelAndView login(String error, String logout) {
        ModelAndView model = new ModelAndView("login");

        if (error != null) {
            model.addObject("error", "Email or password is incorrect.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out successfully.");
        }

        return model;
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping(value = "/403")
    public ModelAndView renderErrorPage() {
        return new ModelAndView("error/403");
    }
}
