package pl.damianrowinski.clients_database.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.clients_database.domain.entities.Client;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findFirstByCompany_Id(Long id);
}
