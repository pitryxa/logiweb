package logiweb.controller;

import logiweb.dto.TruckDto;
import logiweb.entity.enums.TruckConditionStatus;
import logiweb.entity.enums.TruckWorkStatus;
import logiweb.service.api.CityService;
import logiweb.service.api.TruckService;
import logiweb.validator.TruckValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/officer/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TruckValidator truckValidator;

    @GetMapping
    public String allTrucks(Model model) {
        model.addAttribute("trucks", truckService.getAll());
        return "trucks/truckList";
    }

    @GetMapping("/{id}")
    public String truckInfo(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("truck", truckService.getById(id));
        return "trucks/truckInfo";
    }

    @GetMapping("/edit/{id}")
    public String editTruck(@PathVariable("id") int id, Model model) {
        model.addAttribute("truck", truckService.getById(id));
        model.addAttribute("cityList", cityService.getAll());
        model.addAttribute("statusArray", TruckConditionStatus.values());
        return "trucks/editTruck";
    }

    @PostMapping("/edit")
    public String editTruck(@ModelAttribute TruckDto truckDto) {
        TruckDto oldTruck = truckService.getById(truckDto.getId());
        if (!Objects.equals(oldTruck.getRegNumber(), truckDto.getRegNumber()) && !truckValidator.isValid(truckDto)) {
            return "trucks/notValid";
        }

        truckService.edit(truckDto);
        return "redirect:/officer/trucks";
    }

    @GetMapping("/add")
    public String addTruck(Model model) {
        model.addAttribute("cityList", cityService.getAll());
        return "trucks/addTruck";
    }

    @PostMapping("/add")
    public String addTruck(@ModelAttribute TruckDto truckDto) {
        if (!truckValidator.isValid(truckDto)) {
            return "trucks/notValid";
        }

        truckDto.setWorkStatus(TruckWorkStatus.FREE);
        truckDto.setConditionStatus(TruckConditionStatus.OK);
        truckService.add(truckDto);
        return "redirect:/officer/trucks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTruck(@PathVariable("id") int id, Model model) {
        model.addAttribute("truck", truckService.getById(id));
        return "trucks/deleteTruck";
    }

    @PostMapping("/delete")
    public String deleteTruck(@ModelAttribute TruckDto truckDto) {
        truckDto.setConditionStatus(TruckConditionStatus.DISABLED);
        truckService.edit(truckDto);
        //truckService.delete(truckDto);
        return "redirect:/officer/trucks";
    }
}
