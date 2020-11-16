package pl.damianrowinski.clients_database.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyDTO {
    private long id;
    private LocalDateTime modificationDate;
    private String companyName;
    private int nip;
}
