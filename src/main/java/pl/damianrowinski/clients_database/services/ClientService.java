package pl.damianrowinski.clients_database.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import pl.damianrowinski.clients_database.assembler.ClientAssembler;
import pl.damianrowinski.clients_database.domain.dto.ClientDTO;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.domain.entities.Client;
import pl.damianrowinski.clients_database.domain.repositories.ClientRepository;
import pl.damianrowinski.clients_database.exceptions.CompanyNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final CompanyService companyService;
    private final ClientAssembler clientAssembler;

    public List<ClientDTO> findAll() {
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(clientAssembler::convertToDataFromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> findById(Long id) {
        return clientRepository.findById(id)
                .map(clientAssembler::convertToDataFromEntity);
    }

    public ClientDTO add(ClientDTO clientDTO) {
        Optional<CompanyDTO> optionalCompany = companyService.findById(clientDTO.getCompanyData().getId());
        if (optionalCompany.isEmpty())
            throw new CompanyNotFoundException("Company not found, firstly add company, then client.");
        Client clientToAdd = clientAssembler.convertToEntityFromData(clientDTO);
        Client savedClient = clientRepository.save(clientToAdd);
        return clientAssembler.convertToDataFromEntity(savedClient);
    }

}
