package realEstate.salesianos.triana.dam.realEstate.dtos;

import org.springframework.stereotype.Component;
import realEstate.salesianos.triana.dam.realEstate.models.Interesa;
import realEstate.salesianos.triana.dam.realEstate.users.dto.Interesados.InteresadoDtoConverter;

@Component
public class InteresaDtoConverter {
    private final InteresadoDtoConverter interesadoDtoConverter= new InteresadoDtoConverter();

    public GetInteresaDto interesaToGetInteresaDto (Interesa i){

        GetInteresaDto result = new GetInteresaDto();

        result.setInteresado_id(i.getUsuario().getId());
        result.setVivienda_id(i.getVivienda().getId());
        result.setCreatedDate(i.getCreatedDate());
        result.setMensaje(i.getMensaje());
        result.setInteresadoDto(interesadoDtoConverter.interesadoToGetInteresadoDto(i.getUsuario()));

        return result;
    }
}
