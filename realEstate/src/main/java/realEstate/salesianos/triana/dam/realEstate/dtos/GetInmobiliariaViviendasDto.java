package realEstate.salesianos.triana.dam.realEstate.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import realEstate.salesianos.triana.dam.realEstate.models.Vivienda;

import java.util.List;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class GetInmobiliariaViviendasDto extends GetInmobiliariaDto{

    private List<String> Vivienda;

    public GetInmobiliariaViviendasDto(Long id, String nombre, String avatar, int numViviendas, List<String> vivienda) {
        super(id, nombre, avatar, numViviendas);
        this.Vivienda = vivienda;
    }
}
