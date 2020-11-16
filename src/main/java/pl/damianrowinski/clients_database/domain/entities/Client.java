package pl.damianrowinski.clients_database.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Getter @Setter @ToString @EqualsAndHashCode(callSuper = true)
@Table(name = Client.TABLE_NAME)
public class Client extends BaseEntity{
    @Transient
    final static String TABLE_NAME = "clients";

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(optional = false)
    private Company company;
}
