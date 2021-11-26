package realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInmobiliariaGestorDto {

    private Long id;
    private String nombre;
    private String avatar;
    private Long idGestor;
}
