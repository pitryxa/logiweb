package logiweb.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayRestDto {
    private List<OrderRestDto> orders;
    private DriverRestDto drivers;
    private TruckRestDto trucks;
}
