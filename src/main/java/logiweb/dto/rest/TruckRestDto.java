package logiweb.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TruckRestDto {
    private Integer all;
    private Integer free;
    private Integer broken;

    public TruckRestDto(Integer all, Integer free, Integer broken) {
        this.all = all;
        this.free = free;
        this.broken = broken;
    }
}
