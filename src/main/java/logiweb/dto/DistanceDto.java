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
    private String cityFrom;
    private String cityTo;
    private Integer distance;
}
