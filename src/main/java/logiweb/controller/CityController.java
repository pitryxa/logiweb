package logiweb.controller;

import logiweb.dto.CityDto;
import logiweb.service.api.CityService;
import logiweb.validator.CityValidator;
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

    @Autowired
    private CityValidator cityValidator;

    @GetMapping
    public String allCities(Model model) {
        List<CityDto> cities = cityService.getAll();
        model.addAttribute("cityList", cities);
        return "city/cityList";
    }

    @GetMapping("/add")
    public String addCity(Model model) {
        return "city/addCity";
    }

    @PostMapping("/add")
    public String addCity(@ModelAttribute CityDto cityDto) {
        if (cityValidator.isExist(cityDto)) {
            return "city/cityIsExists";
        }
        cityService.add(cityDto);
        return "redirect:/admin/city";
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
