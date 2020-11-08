package logiweb.validator;

import logiweb.dto.DriverDto;
import logiweb.dto.DriverEditDto;
import logiweb.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverValidator {
    @Autowired
    private DriverService driverService;

    public boolean isValid(DriverDto driverDto) {
        return driverService.getByPersonalNumber(driverDto.getPersonalNumber()) == null;
    }

    public boolean isValid(DriverEditDto driverEditDto) {
        return driverService.getByPersonalNumber(driverEditDto.getPersonalNumber()) == null;
    }
}
