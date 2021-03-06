package logiweb.dto;

import logiweb.dto.simple.SimpleDriverDto;
import logiweb.entity.enums.TruckConditionStatus;
import logiweb.entity.enums.TruckWorkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TruckDto implements Serializable {
    private Integer id;

    private String regNumber;

    private Integer shiftSize;

    private Integer capacity;

    private TruckConditionStatus conditionStatus;

    private TruckWorkStatus workStatus;

    private String city;

    private List<SimpleDriverDto> drivers;

    private Integer orderId;

    public void toUpperCaseRegNumber() {
        regNumber = regNumber.toUpperCase();
    }
}
