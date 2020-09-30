package logiweb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
//@Entity
//@Table(name = "distance")
public class Distance {

    @ManyToOne
    @JoinColumn(name = "city_id_a")
    private City cityA;

    @ManyToOne
    @JoinColumn(name = "city_id_b")
    private City cityB;

    @Column
    private int distance;
}
