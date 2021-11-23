package realEstate.salesianos.triana.dam.realEstate.users.dto;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioConViviendasDto;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetPropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.dtos.GetViviendaSinPropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.models.Vivienda;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public GetUserDto convertUsuarioToGetUserDto(Usuario user) {
        return GetUserDto.builder()
                .id(user.getId())
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
                //.numViviendas(numViviendas)
                .build();
    }

    public GetPropietarioConViviendasDto propietarioToGetPropietarioConViviendas(Usuario p) {
        GetPropietarioConViviendasDto dto = new GetPropietarioConViviendasDto(
                p.getId(),
                p.getNombre(),
                p.getApellidos(),
                p.getEmail(),
                p.getAvatar(),
                p.getDireccion(),
                p.getTelefono(),
                p.getViviendas().stream().map(this::convertViviendaToGetViviendaSinPropietarioDto).collect(Collectors.toList()));
        return dto;
    }

    public GetViviendaSinPropietarioDto convertViviendaToGetViviendaSinPropietarioDto(Vivienda v){
        return GetViviendaSinPropietarioDto.builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .avatar(v.getAvatar())
                .poblacion(v.getPoblacion())
                .precio(v.getPrecio())
                .build();

    }
}


