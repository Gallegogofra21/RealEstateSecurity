package realEstate.salesianos.triana.dam.realEstate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
