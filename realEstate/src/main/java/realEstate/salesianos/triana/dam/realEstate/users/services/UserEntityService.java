package realEstate.salesianos.triana.dam.realEstate.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import realEstate.salesianos.triana.dam.realEstate.models.Inmobiliaria;
import realEstate.salesianos.triana.dam.realEstate.security.dto.JwtUserResponse;
import realEstate.salesianos.triana.dam.realEstate.services.InmobiliariaService;
import realEstate.salesianos.triana.dam.realEstate.services.base.BaseService;
import realEstate.salesianos.triana.dam.realEstate.users.dto.CreateUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.model.UserRole;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
import realEstate.salesianos.triana.dam.realEstate.users.repos.UserEntityRepository;

import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<Usuario, Long, UserEntityRepository> implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

    public Page<Usuario> loadUserByRole(UserRole rol, Pageable pageable) throws UsernameNotFoundException{
        return this.repositorio.findByRol(rol, pageable);
    }

    public Optional<Usuario> loadUserById(Long id) throws UsernameNotFoundException{
        return this.repositorio.findById(id);
    }

    public Usuario saveAdmin(CreateUserDto newUser) {
        if(newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getNombre())
                    .email(newUser.getEmail())
                    .rol(UserRole.ADMIN)
                    .build();
            return save(usuario);
        } else {
            return null;
        }
    }

    
    public Usuario savePropietario(CreateUserDto newUser) {
        if(newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getNombre())
                    .email(newUser.getEmail())
                    .rol(UserRole.PROPIETARIO)
                    .build();
            return save(usuario);
        } else {
            return null;
        }
    }

    public Usuario saveGestor(CreateUserDto newUser) {
        if(newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getNombre())
                    .email(newUser.getEmail())
                    .rol(UserRole.GESTOR)
                    //.inmobiliaria(null)
                    .build();

            /*Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(newUser.getInmobiliaria_id());
            usuario.addInmobiliaria(inmobiliaria.get());*/
            return save(usuario);
        } else {
            return null;
        }
    }
}
