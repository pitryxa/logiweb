package logiweb.entity;

import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "waypoints")
public class WaypointEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private OperationTypeOnWaypoint operation;

    @Column(name = "sequential_number")
    private Integer sequentialNumber;
}
