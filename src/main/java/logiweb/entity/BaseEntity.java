package logiweb.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {
    @Id
    @Column
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Integer id;
}
