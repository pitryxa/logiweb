package logiweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/hello")
    public String getHelloPage() {
        return "hello";
    }

    @GetMapping("/")
    public String getStartPage() {
        return "redirect:/hello";
    }
}
