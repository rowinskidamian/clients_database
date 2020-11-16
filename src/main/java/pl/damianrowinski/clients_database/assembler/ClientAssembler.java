package pl.damianrowinski.clients_database.assembler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.clients_database.domain.dto.ClientDTO;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.domain.entities.Client;
import pl.damianrowinski.clients_database.domain.entities.Company;

@Component
@RequiredArgsConstructor
public class ClientAssembler {
    private final ModelMapper modelMapper;

    public ClientDTO convertToDataFromEntity(Client client){
        ClientDTO clientData = modelMapper.map(client, ClientDTO.class);
        CompanyDTO companyData = modelMapper.map(client.getCompany(), CompanyDTO.class);
        clientData.setCompany(companyData);
        return clientData;
    }

    public Client convertToEntityFromData(ClientDTO clientDTO) {
        Client clientConverted = modelMapper.map(clientDTO, Client.class);
        Company companyConverted = modelMapper.map(clientDTO.getCompany(), Company.class);
        clientConverted.setCompany(companyConverted);
        return clientConverted;
    }
}
