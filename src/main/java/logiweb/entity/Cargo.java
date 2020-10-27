package logiweb.entity;

import logiweb.entity.enums.CargoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "cargo")
public class Cargo extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CargoStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id_from")
    private City cityFrom;

    @ManyToOne
    @JoinColumn(name = "city_id_to")
    private City cityTo;
}
