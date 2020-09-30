package logiweb.controller;

import logiweb.dto.CityDto;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public ModelAndView allCities() {
        ModelAndView model = new ModelAndView();
        model.setViewName("city/cityList");
        model.addObject("cityList", cityService.getAll());
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCity(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("city/deleteCity");
        model.addObject("city", cityService.getById(id));
        return model;
    }

    @PostMapping("/delete")
    public ModelAndView deleteCity(@ModelAttribute CityDto cityDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/city");
        cityService.delete(cityDTO);
        return model;
    }
}
