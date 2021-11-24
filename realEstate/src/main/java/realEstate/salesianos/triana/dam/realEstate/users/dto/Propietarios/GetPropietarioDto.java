package realEstate.salesianos.triana.dam.realEstate.users.dto.Propietarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPropietarioDto {

    private Long id;
    private String avatar;
    private String nombre;
    private String email;
    private String role;
    private int numViviendas;
}
