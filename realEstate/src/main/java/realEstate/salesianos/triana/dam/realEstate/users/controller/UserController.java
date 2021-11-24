package realEstate.salesianos.triana.dam.realEstate.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioConViviendasDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.CreateUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores.CreateGestorDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Propietarios.GetPropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.UserDtoConverter;
import realEstate.salesianos.triana.dam.realEstate.users.model.UserRole;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
import realEstate.salesianos.triana.dam.realEstate.users.repos.UserEntityRepository;
import realEstate.salesianos.triana.dam.realEstate.users.services.UserEntityService;
import realEstate.salesianos.triana.dam.realEstate.util.PaginationLinksUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserEntityRepository repository;
    private final UserDtoConverter userDtoConverter;
    private final PaginationLinksUtil paginationLinksUtil;


    @PostMapping("/auth/register/admin")
    public ResponseEntity<CreateUserDto> nuevoAdmin(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.saveAdmin(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToNewUser(saved));
    }

    @PostMapping("/auth/register/user")
    public ResponseEntity<CreateUserDto> nuevoPropietario(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.savePropietario(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToNewUser(saved));
    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<CreateGestorDto> nuevoGestor(@RequestBody CreateGestorDto newUser) {
        Usuario saved = userEntityService.saveGestor(newUser);

        if (saved == null) //|| saved.getInmobiliaria() == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGestorDto(saved));
    }

    @GetMapping("/propietario")
    public ResponseEntity<?> findAllPropietarios(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            HttpServletRequest request) {

        Page<Usuario> data = userEntityService.loadUserByRole(UserRole.PROPIETARIO, pageable);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Page<GetPropietarioDto> result = data.map(userDtoConverter::convertUsuarioToGetPropietarioDto);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("link", paginationLinksUtil.createLinkHeader(result, uriBuilder)).body(result);
        }
    }

    @GetMapping("/propietario/{id}")
    public ResponseEntity<List<GetPropietarioConViviendasDto>> findOnePropietario(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Optional<Usuario> propietario = userEntityService.loadUserById(id);

        if(propietario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else if(usuario.getRol().equals(UserRole.ADMIN) || (propietario.get().getRol().equals(usuario.getRol())
                && propietario.get().getId().equals(usuario.getId()))){
            List<GetPropietarioConViviendasDto> propietarioDto = propietario.stream()
                    .map(userDtoConverter::convertPropietarioToGetPropietarioConViviendasDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(propietarioDto);
        }else {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/propietario/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {

        Optional<Usuario> propietarioOptional = userEntityService.loadUserById(id);

        if(propietarioOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else if(usuario.getRol().equals(UserRole.ADMIN) || (propietarioOptional.get().getRol().equals(usuario.getRol()) &&
                propietarioOptional.get().getId().equals(usuario.getId()))) {
            Usuario propietario = propietarioOptional.get();
            propietario.nullearPropietarioDeViviendas();
            repository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
