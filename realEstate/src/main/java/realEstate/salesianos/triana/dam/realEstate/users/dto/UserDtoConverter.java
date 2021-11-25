package realEstate.salesianos.triana.dam.realEstate.users.dto;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.dtos.*;
import realEstate.salesianos.triana.dam.realEstate.models.Interesa;
import realEstate.salesianos.triana.dam.realEstate.models.Vivienda;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores.CreateGestorDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Propietarios.CreatePropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Propietarios.GetPropietarioDto;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public GetPropietarioConViviendasDto convertPropietarioToGetPropietarioConViviendasDto(Usuario p){
        GetPropietarioConViviendasDto dto= new GetPropietarioConViviendasDto(
                p.getId(),
                p.getNombre(),
                p.getApellidos(),
                p.getDireccion(),
                p.getEmail(),
                p.getTelefono(),
                p.getAvatar(),
                p.getViviendas().stream().map(this::convertViviendaToGetViviendaSinPropietarioDto).collect(Collectors.toList()));
        return dto;
    }

    public GetPropietarioDto convertUsuarioToGetPropietarioDto (Usuario user) {
        int numViviendas = user.getViviendas().size();
        return GetPropietarioDto.builder()
                .nombre(user.getNombre())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .role(user.getRol().name())
                .numViviendas(numViviendas)
                .build();
    }

    public CreateUserDto convertUsuarioToNewUser(Usuario p) {
        return CreateUserDto.builder()
                .username(p.getUsername())
                .email(p.getEmail())
                .avatar(p.getAvatar())
                .password(p.getPassword())
                .password2(p.getPassword())
                .nombre(p.getNombre())
                .build();
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

    public CreateGestorDto convertUsuarioToGestorDto (Usuario g) {
        return CreateGestorDto.builder()
                .username(g.getUsername())
                .avatar(g.getAvatar())
                .nombre(g.getNombre())
                .email(g.getEmail())
                .password(g.getPassword())
                .password2(g.getPassword())
                //.inmobiliariaId(g.getInmobiliaria().getId())
                .build();
    }

}


