package logiweb.entity;

import logiweb.entity.enums.TruckStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "truck")
public class Truck extends BaseEntity {
    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "shift_size")
    private Integer shiftSize;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TruckStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    private List<Driver> drivers;

    @Override
    public String toString() {
        return "Truck{" +
                "regNumber='" + regNumber + '\'' +
                ", shiftSize=" + shiftSize +
                ", capacity=" + capacity +
                ", status=" + status +
                '}';
    }
}
