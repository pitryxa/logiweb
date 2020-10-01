package logiweb.dto.simple;

import logiweb.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleDriverDto {
    private Integer id;
    private UserDto user;
}
