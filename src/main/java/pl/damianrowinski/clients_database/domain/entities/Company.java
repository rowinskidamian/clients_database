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
@Table(name = Company.TABLE_NAME)
public class Company extends BaseEntity{
    @Transient
    final static String TABLE_NAME = "companies";

    @Column(name ="company_name", nullable = false)
    private String companyName;

    @Column(name = "nip", nullable = false)
    private int nip;
}
