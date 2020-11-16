package pl.damianrowinski.clients_database.domain.dto;

import lombok.Data;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Data
@Transactional
public class ClientDTO {
    private long id;
    private LocalDateTime modificationDate;
    private String firstName;
    private String lastName;
    private CompanyDTO company;
}
