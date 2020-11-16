package pl.damianrowinski.clients_database.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damianrowinski.clients_database.services.ClientService;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final ClientService clientService;
}
