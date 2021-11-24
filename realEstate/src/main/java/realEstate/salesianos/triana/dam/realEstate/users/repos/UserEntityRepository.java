package realEstate.salesianos.triana.dam.realEstate.users.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import realEstate.salesianos.triana.dam.realEstate.security.dto.JwtUserResponse;
import realEstate.salesianos.triana.dam.realEstate.users.dto.GetUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.model.UserRole;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstByEmail(String email);

    Page<Usuario> findByRol(UserRole role, Pageable pageable);

    Optional<Usuario> findById(Long id);
}

