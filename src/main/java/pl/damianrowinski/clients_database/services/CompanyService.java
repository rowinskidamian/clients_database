package pl.damianrowinski.clients_database.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.clients_database.domain.repositories.CompanyRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAssembler companyAssembler;


}
