package logiweb.validator;

import logiweb.dto.DistanceDto;
import logiweb.service.api.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistanceValidator {
    @Autowired
    private DistanceService distanceService;

    public boolean isExist(DistanceDto distanceDto) {
        return distanceService.getDistance(distanceDto.getCityFrom(), distanceDto.getCityTo()) != null;
    }
}
