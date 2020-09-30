package logiweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class LoginController {
//    @GetMapping("/")
//    public ModelAndView getLoginPage() {
//        ModelAndView model = new ModelAndView();
//        model.setViewName("login");
//        return model;
//    }

    @GetMapping("/hello")
    public ModelAndView getHelloPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("hello");
        return model;
    }

    @GetMapping("/")
    public ModelAndView getStartPage() {
        ModelAndView model = new ModelAndView("redirect:/hello");

        return model;
    }
}
