package logiweb.controller;

import logiweb.dto.rest.DisplayRestDto;
import logiweb.service.api.DisplayRestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class DisplayRestController {
    private static final Logger logger = Logger.getLogger(DisplayRestController.class);
    @Autowired
    private DisplayRestService displayRestService;

    @GetMapping("/update-display")
    public DisplayRestDto getDisplayRestDto() {
        logger.info("Data is sent to display");
        return displayRestService.getDisplayRestDto();
    }
}


