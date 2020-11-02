package logiweb.service;

import logiweb.dto.rest.DisplayRestDto;
import logiweb.dto.rest.DriverRestDto;
import logiweb.dto.rest.TruckRestDto;
import logiweb.service.api.DisplayRestService;
import logiweb.service.api.DriverService;
import logiweb.service.api.OrderService;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayRestServiceImpl implements DisplayRestService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private TruckService truckService;

    @Override
    public DisplayRestDto getDisplayRestDto() {
        return new DisplayRestDto(orderService.getTenLast(), driverService.getDriverRestDto(),
                                  truckService.getTruckRestDto());
    }
}
