package logiweb.entity;

import logiweb.entity.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "driver")
public class Driver extends BaseEntity {
    @Column(name = "personal_number")
    private Long personalNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hours")
    private Double workHours;

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

    @Override
    public String toString() {
        return "Driver{" + "personalNumber=" + personalNumber + ", user=" + user + ", workHours=" + workHours +
               ", status=" + status + ", truck=" + truck + ", city=" + city + ", timeLastChangeStatus=" +
               timeLastChangeStatus + "} " + super.toString();
    }


}
