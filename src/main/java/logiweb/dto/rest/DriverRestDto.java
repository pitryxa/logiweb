package logiweb.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverRestDto {
    private Integer all;
    private Integer free;

    public DriverRestDto(Integer all, Integer free) {
        this.all = all;
        this.free = free;
    }
}
