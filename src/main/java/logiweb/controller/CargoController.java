package logiweb.controller;

import logiweb.model.Cargo;
import logiweb.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CargoController {
    private CargoService cargoService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/")
    public ModelAndView getHelloPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("hello");
        return model;
    }

    @GetMapping("/cargo")
    public ModelAndView allCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/cargoList");
        model.addObject("cargoList", cargoService.allCargo());
        return model;
    }

    @GetMapping("/edit-cargo/{id}")
    public ModelAndView editCargo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/editCargo");
        model.addObject("cargo", cargoService.getById(id));
        return model;
    }

    @PostMapping("/edit-cargo")
    public ModelAndView editCargo(@ModelAttribute Cargo cargo) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.edit(cargo);
        return model;
    }

    @GetMapping("/add-cargo")
    public ModelAndView addCargo() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/addCargo");
        //model.addObject("cargo", cargoService.getById(id));
        return model;
    }

    @PostMapping("/add-cargo")
    public ModelAndView addCargo(@ModelAttribute Cargo cargo) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.add(cargo);
        return model;
    }

    @GetMapping("/delete-cargo/{id}")
    public ModelAndView deleteCargo(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("cargo/deleteCargo");
        model.addObject("cargo", cargoService.getById(id));
        return model;
    }

    @PostMapping("/delete-cargo")
    public ModelAndView deleteCargo(@ModelAttribute Cargo cargo) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/cargo");
        cargoService.delete(cargo);
        return model;
    }
}
