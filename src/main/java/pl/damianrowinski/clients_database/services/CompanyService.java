package pl.damianrowinski.clients_database.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.clients_database.assembler.CompanyAssembler;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.domain.entities.Company;
import pl.damianrowinski.clients_database.domain.repositories.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAssembler companyAssembler;

    public List<CompanyDTO> findAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream()
                .map(companyAssembler::convertToDataFromEntity)
                .collect(Collectors.toList());
    }

    public Optional<CompanyDTO> findById(Long id) {
        return companyRepository.findById(id)
                .map(companyAssembler::convertToDataFromEntity);
    }

    public CompanyDTO add(CompanyDTO companyDTO) {
        Company companyToAdd = companyAssembler.convertToEntityFromData(companyDTO);
        Company savedCompany = companyRepository.save(companyToAdd);
        return companyAssembler.convertToDataFromEntity(savedCompany);
    }

    public Optional<CompanyDTO> edit(CompanyDTO companyDTO) {
        Optional<Company> optionalCompany = companyRepository.findById(companyDTO.getId());
        if (optionalCompany.isEmpty()) return Optional.empty();
        Company companyToUpdate = companyAssembler.convertToEntityFromData(companyDTO);
        companyRepository.save(companyToUpdate);
        return Optional.of(companyDTO);
    }

    public Optional<CompanyDTO> delete (Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) return Optional.empty();
        Company companyToDelete = optionalCompany.get();
        companyRepository.delete(companyToDelete);
        CompanyDTO companyDTO = companyAssembler.convertToDataFromEntity(companyToDelete);
        return Optional.of(companyDTO);
    }

}
