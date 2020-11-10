package logiweb.validator;

import logiweb.dto.CityDto;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityValidator {
    @Autowired
    private CityService cityService;

    public boolean isExist(CityDto cityDto) {
        return cityService.getByName(cityDto.getName()) != null;
    }
}
