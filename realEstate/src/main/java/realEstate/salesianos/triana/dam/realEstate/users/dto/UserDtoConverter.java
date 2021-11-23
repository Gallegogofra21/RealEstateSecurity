package realEstate.salesianos.triana.dam.realEstate.users.dto;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioDto;
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

    public GetUserDto convertUsuarioToGetPropietarioDto (Usuario user) {
        int numViviendas = user.getViviendas().size();
        return GetUserDto.builder()
                .nombre(user.getNombre())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .role(user.getRol().name())
                .numViviendas(numViviendas)
                .build();
    }
}
