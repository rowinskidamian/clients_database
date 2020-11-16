package pl.damianrowinski.clients_database.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@Transactional
@Getter @Setter @ToString @EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime operationDate;

    @PrePersist
    public void addOperationDate() {
        updateDate();
    }

    @PreUpdate
    public void editOperationDate() {
        updateDate();
    }

    private void updateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-y HH:mm");
        String currentDateFormatted = LocalDateTime.now().format(formatter);
        operationDate = LocalDateTime.parse(currentDateFormatted, formatter);
    }
}
