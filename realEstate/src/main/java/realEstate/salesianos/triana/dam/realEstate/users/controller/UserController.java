package realEstate.salesianos.triana.dam.realEstate.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import realEstate.salesianos.triana.dam.realEstate.users.dto.CreateUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.GetUserDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.UserDtoConverter;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
import realEstate.salesianos.triana.dam.realEstate.users.services.UserEntityService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> nuevoAdmin(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.saveAdmin(newUser);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }
    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoPropietario (@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.savePropietario(newUser);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@RequestBody CreateUserDto newUser) {
        Usuario saved = userEntityService.saveGestor(newUser);

        if(saved == null || saved.getViviendas() == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUsuarioToGetUserDto(saved));
    }
}
