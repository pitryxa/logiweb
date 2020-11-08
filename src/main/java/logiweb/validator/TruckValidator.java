package logiweb.validator;

import logiweb.dto.TruckDto;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TruckValidator {
    @Autowired
    private TruckService truckService;

    public boolean isValid(TruckDto truckDto) {
        return truckService.getByRegNumber(truckDto.getRegNumber()) == null;
    }
}
