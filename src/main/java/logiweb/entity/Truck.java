package logiweb.entity;

import logiweb.entity.enums.TruckConditionStatus;
import logiweb.entity.enums.TruckWorkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "condition_status")
    @Enumerated(EnumType.STRING)
    private TruckConditionStatus conditionStatus;

    @Column(name = "work_status")
    @Enumerated(EnumType.STRING)
    private TruckWorkStatus workStatus;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "truck", fetch = FetchType.EAGER)
    private List<Driver> drivers;

    @Override
    public String toString() {
        return "Truck{" +
                "regNumber='" + regNumber + '\'' +
                ", shiftSize=" + shiftSize +
                ", capacity=" + capacity +
                ", conditionStatus=" + conditionStatus +
                '}';
    }
}
