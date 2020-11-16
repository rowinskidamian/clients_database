package pl.damianrowinski.clients_database.assembler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.domain.entities.Company;

@Component
@RequiredArgsConstructor
public class CompanyAssembler {
    private final ModelMapper modelMapper;

    public CompanyDTO convertToDataFromEntity(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }

    public Company convertToEntityFromData(CompanyDTO companyDTO) {
        return modelMapper.map(companyDTO, Company.class);
    }

}
