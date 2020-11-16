package pl.damianrowinski.clients_database.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.clients_database.assembler.ClientAssembler;
import pl.damianrowinski.clients_database.domain.dto.ClientDTO;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.domain.entities.Client;
import pl.damianrowinski.clients_database.domain.repositories.ClientRepository;
import pl.damianrowinski.clients_database.exceptions.CompanyNotFoundException;
import pl.damianrowinski.clients_database.exceptions.ObjectInRelationshipException;

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
        throwIfClientsCompanyIsNotPresent(clientDTO);
        throwIfCompanyHasGotClient(clientDTO);

        Client clientToAdd = clientAssembler.convertToEntityFromData(clientDTO);
        Client savedClient = clientRepository.save(clientToAdd);
        return clientAssembler.convertToDataFromEntity(savedClient);
    }

    public Optional<ClientDTO> edit(ClientDTO clientDTO) {
        throwIfClientsCompanyIsNotPresent(clientDTO);
        throwIfCompanyHasGotClient(clientDTO);

        Optional<Client> optionalClient = clientRepository.findById(clientDTO.getId());
        if (optionalClient.isEmpty()) return Optional.empty();
        Client clientToUpdate = clientAssembler.convertToEntityFromData(clientDTO);
        Client updatedClient = clientRepository.save(clientToUpdate);
        ClientDTO updatedClientDTO = clientAssembler.convertToDataFromEntity(updatedClient);
        return Optional.of(updatedClientDTO);
    }

    public Optional<ClientDTO> delete(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) return Optional.empty();
        Client clientToDelete = optionalClient.get();
        clientRepository.delete(clientToDelete);
        ClientDTO clientDTO = clientAssembler.convertToDataFromEntity(clientToDelete);
        return Optional.of(clientDTO);
    }

    private void throwIfClientsCompanyIsNotPresent(ClientDTO clientDTO) {
        Optional<CompanyDTO> optionalCompany = companyService.findById(clientDTO.getCompany().getId());
        if (optionalCompany.isEmpty())
            throw new CompanyNotFoundException("Company not found, firstly add company, then client.");
    }

    private void throwIfCompanyHasGotClient(ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository.findFirstByCompany_Id(clientDTO.getCompany().getId());
        if (optionalClient.isPresent())
            throw new ObjectInRelationshipException
                    ("Company is already connected to some client, please add different company.");
    }

}
