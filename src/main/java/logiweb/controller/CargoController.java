package logiweb.controller;

import logiweb.dto.CargoDto;
import logiweb.entity.enums.CargoStatus;
import logiweb.service.api.CargoService;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/officer/cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public String allCargo(Model model) {
        model.addAttribute("cargoList", cargoService.getAll());
        return "cargo/cargoList";
    }

    @GetMapping("/edit/{id}")
    public String editCargo(@PathVariable("id") int id, Model model) {
        model.addAttribute("cargo", cargoService.getById(id));
        model.addAttribute("cityList", cityService.getAll());
        model.addAttribute("statusArray", CargoStatus.values());
        return "cargo/editCargo";
    }

    @PostMapping("/edit")
    public String editCargo(@ModelAttribute CargoDto cargoDTO) {
        cargoService.edit(cargoDTO);
        return "redirect:/officer/cargo";
    }

    @GetMapping("/add")
    public String addCargo(Model model) {
        model.addAttribute("cityList", cityService.getAll());
        model.addAttribute("statusArray", CargoStatus.values());
        return "cargo/addCargo";
    }

    @PostMapping("/add")
    public String addCargo(@ModelAttribute CargoDto cargoDTO) {
        cargoService.add(cargoDTO);
        return "redirect:/officer/cargo";
    }

    @GetMapping("/delete/{id}")
    public String deleteCargo(@PathVariable("id") int id, Model model) {
        model.addAttribute("cargo", cargoService.getById(id));
        return "cargo/deleteCargo";
    }

    @PostMapping("/delete")
    public String deleteCargo(@ModelAttribute CargoDto cargoDTO) {
        cargoService.delete(cargoDTO);
        return "redirect:/officer/cargo";
    }
}
