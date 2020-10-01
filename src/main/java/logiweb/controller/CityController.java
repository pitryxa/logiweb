package logiweb.controller;

import logiweb.dto.CityDto;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public String allCities(Model model) {
        model.addAttribute("cityList", cityService.getAll());
        return "city/cityList";
    }

    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable("id") int id, Model model) {
        model.addAttribute("city", cityService.getById(id));
        return "city/deleteCity";
    }

    @PostMapping("/delete")
    public String deleteCity(@ModelAttribute CityDto cityDTO) {
        cityService.delete(cityDTO);
        return "redirect:/admin/city";
    }
}
