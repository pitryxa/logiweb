package logiweb.temp;

import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Temp {
    @Autowired
    private CityService cityService;

    public CityService getCityService() {
        return cityService;
    }
}
