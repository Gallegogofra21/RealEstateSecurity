package realEstate.salesianos.triana.dam.realEstate.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstByEmail(String email);
}

