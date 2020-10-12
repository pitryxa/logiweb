package logiweb.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DistanceDto {
    private Integer id;
    private CityDto cityFrom;
    private CityDto cityTo;
    private Integer distance;
}
