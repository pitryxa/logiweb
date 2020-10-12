package logiweb.controller;

import logiweb.dto.CityDto;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public String allCities(Model model) {
        List<CityDto> cities = cityService.getAll();
        model.addAttribute("cityList", cities);

        //Long count = cityService.countOfCities();

        //Route route = cityService.minRoute(cities.get(0), cities.get(18));

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
