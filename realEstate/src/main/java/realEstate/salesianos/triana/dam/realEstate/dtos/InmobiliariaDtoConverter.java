package realEstate.salesianos.triana.dam.realEstate.dtos;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.models.Inmobiliaria;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores.GetInmobiliariaGestorDto;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import java.util.ArrayList;
import java.util.List;

@Component
public class InmobiliariaDtoConverter {

    public Inmobiliaria createInmobiliariaDtoToInmobiliaria(GetInmobiliariaDto c){
        return new Inmobiliaria(
                c.getNombre(),
                c.getAvatar()
        );
    }

    public GetInmobiliariaDto inmobiliariaToGetInmobiliariaDto(Inmobiliaria i){
        int numViviendas = i.getViviendas().size();
        GetInmobiliariaDto result = new GetInmobiliariaDto();
        result.setId(i.getId());
        result.setNombre(i.getNombre());
        result.setAvatar(i.getAvatar());
        result.setNumViviendas(numViviendas);
        return result;

    }
    public GetInmobiliariaViviendasDto inmobiliariaToGetInmobiliariaViviendasDto(Inmobiliaria i){
        List<String> nombres = new ArrayList<>();
        for (int j = 0; j < i.getViviendas().size(); j++){
            nombres.add(i.getViviendas().get(j).getTitulo());
        }
        return GetInmobiliariaViviendasDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .avatar(i.getAvatar())
                .numViviendas(i.getViviendas().size())
                .Vivienda(nombres)
                .build();
    }

    public GetInmobiliariaDetailDto inmobiliariaToGetInmobiliariaDetailDto(Inmobiliaria i){
        GetInmobiliariaDetailDto result = new GetInmobiliariaDetailDto();
        result.setId(i.getId());
        result.setNombre(i.getNombre());
        result.setEmail(i.getEmail());
        result.setAvatar(i.getAvatar());
        result.setTelefono(i.getTelefono());
        return result;
    }

    public GetInmobiliariaGestorDto inmobiliariaToGetInmobiliariaGestorDto(Inmobiliaria i, Usuario u){
        List<String> nombreGestores = new ArrayList<>();
        for(int j = 0; j < i.getGestores().size(); j++){
            nombreGestores.add(i.getGestores().get(j).getNombre());
        }

        return GetInmobiliariaGestorDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .avatar(i.getAvatar())
                .idGestor(u.getId())
                .build();
    }
}
