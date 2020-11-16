package pl.damianrowinski.clients_database.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.damianrowinski.clients_database.domain.dto.ClientDTO;
import pl.damianrowinski.clients_database.services.ClientService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final ClientService clientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientDTO> getClientList() {
        return clientService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO client) {
        ClientDTO clientCreated = clientService.add(client);
        URI clientLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientCreated.getId())
                .toUri();
        return ResponseEntity.created(clientLocation)
                .body(clientCreated);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO client, @PathVariable Long id) {
        client.setId(id);
        return clientService.edit(client)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
        return clientService.delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
