package realEstate.salesianos.triana.dam.realEstate.users.controller;

import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioConViviendasDto;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.security.dto.JwtUserResponse;
import realEstate.salesianos.triana.dam.realEstate.security.jwt.JwtAuthorizationFilter;
import realEstate.salesianos.triana.dam.realEstate.security.jwt.JwtProvider;
import realEstate.salesianos.triana.dam.realEstate.users.dto.CreateUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.GetUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.UserDtoConverter;
import realEstate.salesianos.triana.dam.realEstate.users.model.UserRole;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
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
    private final UserDtoConverter userDtoConverter;
    private final PaginationLinksUtil paginationLinksUtil;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> nuevoAdmin(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.saveAdmin(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoPropietario(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.savePropietario(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.saveGestor(newUser);

        if (saved == null) //|| saved.getInmobiliaria() == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }

    @GetMapping("/propietario")
    public ResponseEntity<?> findAllPropietarios(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            HttpServletRequest request) {

        Page<Usuario> data = userEntityService.loadUserByRole(UserRole.PROPIETARIO, pageable);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Page<GetUserDto> result = data.map(userDtoConverter::convertUsuarioToGetUserDto);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("link", paginationLinksUtil.createLinkHeader(result, uriBuilder)).body(result);
        }
    }

    @GetMapping("/propietario/{id}")
    public ResponseEntity<List<GetPropietarioConViviendasDto>> findOnePropietario(@PathVariable Long id, HttpServletRequest request) {
        Optional<Usuario> propietario = userEntityService.loadUserById(id /*UserRole.PROPIETARIO*/);

        String token = jwtAuthorizationFilter.getJwtFromRequest(request);
        Long idPropietario = jwtProvider.getUserIdFromJwt(token);

        if(!propietario.get().getRol().equals(UserRole.ADMIN) && !propietario.get().getRol().equals(idPropietario)){
            return ResponseEntity.notFound().build();
        }else {
            List<GetPropietarioConViviendasDto> propietarioDto = propietario.stream()
                    .map(userDtoConverter::propietarioToGetPropietarioConViviendas)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(propietarioDto);
        }
        //return ResponseEntity.of(userEntityService.findById(id).map(userDtoConverter::propietarioToGetPropietarioConViviendas));
    }
}
