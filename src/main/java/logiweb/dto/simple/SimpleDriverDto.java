package logiweb.dto.simple;

import logiweb.dto.UserDto;
import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleDriverDto {
    private Integer id;
    private Long personalNumber;
    private UserDto user;
    private DriverStatus status;
}
