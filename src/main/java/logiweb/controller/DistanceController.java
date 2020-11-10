package logiweb.controller;

import logiweb.dto.CityDto;
import logiweb.dto.DistanceDto;
import logiweb.service.api.CityService;
import logiweb.service.api.DistanceService;
import logiweb.validator.CityValidator;
import logiweb.validator.DistanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/distance")
public class DistanceController {
    @Autowired
    private DistanceService distanceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DistanceValidator distanceValidator;

    @GetMapping("/add")
    public String addDistance(Model model) {
        model.addAttribute("cities", cityService.getAll());
        return "city/addDistance";
    }

    @PostMapping("/add")
    public String addDistance(@ModelAttribute DistanceDto distanceDto) {
        if (distanceValidator.isExist(distanceDto)) {
            return "city/distanceIsExists";
        }
        distanceService.add(distanceDto);
        return "redirect:/admin/city";
    }
}
