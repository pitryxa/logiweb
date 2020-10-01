package logiweb.entity;

import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "driver")
public class Driver extends BaseEntity {
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "surname")
//    private String surname;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hours")
    private Integer workHours;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "time_last_change_status")
    private LocalDateTime timeLastChangeStatus;
}
