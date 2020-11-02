package logiweb.entity;

import logiweb.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "orders")
public class Order extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "truck_id")
    @ToString.Include
    private Truck truck;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @ToString.Include
    private OrderStatus status;

    @Column(name = "distance")
    @ToString.Include
    private Integer distance;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<WaypointEntity> waypointEntities;

//    @Column(name = "time_created_order")
//    private LocalDateTime timeCreatedOrder;
}
