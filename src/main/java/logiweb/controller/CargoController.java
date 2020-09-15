package logiweb.controller;

import logiweb.dto.CargoDTO;
import logiweb.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/cargo")
public class CargoController {
    private CargoService cargoService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("")
    public ModelAndView allCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/cargoList");



        model.addObject("cargoList", cargoService.allCargo());
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCargo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/editCargo");
        model.addObject("cargo", cargoService.getById(id));
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editCargo(@ModelAttribute CargoDTO cargoDTO) throws UnsupportedEncodingException {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.edit(cargoDTO);
        return model;
    }

    @GetMapping("/add")
    public ModelAndView addCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/addCargo");
        return model;
    }

    @PostMapping("/add")
    public ModelAndView addCargo(@ModelAttribute CargoDTO cargoDTO) throws UnsupportedEncodingException {
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
    public ModelAndView deleteCargo(@ModelAttribute CargoDTO cargoDTO) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.delete(cargoDTO);
        return model;
    }
}
