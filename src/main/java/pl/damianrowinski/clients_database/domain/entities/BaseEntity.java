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
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @PrePersist
    public void addModificationDate() {
        updateModificationDate();
    }

    @PreUpdate
    public void editModificationDate() {
        updateModificationDate();
    }

    private void updateModificationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-y HH:mm");
        String currentDateFormatted = LocalDateTime.now().format(formatter);
        modificationDate = LocalDateTime.parse(currentDateFormatted, formatter);
    }
}
