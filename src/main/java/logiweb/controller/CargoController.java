package logiweb.controller;

import logiweb.dto.CargoDto;
import logiweb.entity.enums.CargoStatus;
import logiweb.service.api.CargoService;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/officer/cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ModelAndView allCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/cargoList");
        model.addObject("cargoList", cargoService.getAll());
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCargo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/editCargo");
        model.addObject("cargo", cargoService.getById(id));
        model.addObject("cityList", cityService.getAll());
        model.addObject("statusArray", CargoStatus.values());
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editCargo(@ModelAttribute CargoDto cargoDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.edit(cargoDTO);
        return model;
    }

    @GetMapping("/add")
    public ModelAndView addCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/addCargo");
        model.addObject("cityList", cityService.getAll());
        model.addObject("statusArray", CargoStatus.values());
        return model;
    }

    @PostMapping("/add")
    public ModelAndView addCargo(@ModelAttribute CargoDto cargoDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.add(cargoDTO);
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCargo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/deleteCargo");
        model.addObject("cargo", cargoService.getById(id));
        return model;
    }

    @PostMapping("/delete")
    public ModelAndView deleteCargo(@ModelAttribute CargoDto cargoDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.delete(cargoDTO);
        return model;
    }
}
