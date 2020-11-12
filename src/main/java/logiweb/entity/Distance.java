package logiweb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "distance")
public class Distance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "city_id_from")
    private City cityFrom;

    @ManyToOne
    @JoinColumn(name = "city_id_to")
    private City cityTo;

    @Column(name = "distance")
    private int distance;
}
