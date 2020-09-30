package logiweb.controller;

import logiweb.dto.CargoDto;
import logiweb.dto.TruckDto;
import logiweb.entity.enums.CargoStatus;
import logiweb.entity.enums.TruckStatus;
import logiweb.service.api.CargoService;
import logiweb.service.api.CityService;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/officer/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ModelAndView allTrucks() {
        ModelAndView model = new ModelAndView();
        model.setViewName("trucks/truckList");

        model.addObject("trucks", truckService.getAll());
        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView truckInfo(@PathVariable("id") Integer id) {
        ModelAndView model = new ModelAndView("trucks/truckInfo");
        model.addObject("truck", truckService.getById(id));
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editTruck(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("trucks/editTruck");
        model.addObject("truck", truckService.getById(id));
        model.addObject("cityList", cityService.getAll());
        model.addObject("statusArray", TruckStatus.values());
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editTruck(@ModelAttribute TruckDto truckDto) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/officer/trucks");
        truckService.edit(truckDto);
        return model;
    }

    @GetMapping("/add")
    public ModelAndView addTruck() {
        ModelAndView model = new ModelAndView();
        model.setViewName("trucks/addTruck");
        model.addObject("cityList", cityService.getAll());
        model.addObject("statusArray", TruckStatus.values());
        return model;
    }

    @PostMapping("/add")
    public ModelAndView addTruck(@ModelAttribute TruckDto truckDto) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/officer/trucks");
        truckService.add(truckDto);
        return model;
    }
}
