package pl.damianrowinski.clients_database.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.damianrowinski.clients_database.domain.dto.CompanyDTO;
import pl.damianrowinski.clients_database.services.CompanyService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyService companyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDTO> getCompanyList() {
        return companyService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO company) {
        CompanyDTO companyCreated = companyService.add(company);
        URI companyLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(companyCreated.getId())
                .toUri();
        return ResponseEntity.created(companyLocation)
                .body(companyCreated);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO company, @PathVariable Long id) {
        company.setId(id);
        return companyService.edit(company)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable Long id) {
        return companyService.delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
