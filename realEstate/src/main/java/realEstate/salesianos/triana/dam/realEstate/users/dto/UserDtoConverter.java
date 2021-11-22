package realEstate.salesianos.triana.dam.realEstate.users.dto;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

@Component
public class UserDtoConverter {

    public GetUserDto convertUsuarioToGetUserDto(Usuario user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .role(user.getRol().name())
                .build();
    }
}
