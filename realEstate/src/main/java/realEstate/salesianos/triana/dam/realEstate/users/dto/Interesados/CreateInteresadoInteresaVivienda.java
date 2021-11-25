package realEstate.salesianos.triana.dam.realEstate.users.dto.Interesados;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreateInteresadoInteresaVivienda {
    private String username;
    private String avatar;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    private Long interesado_id;
    private Long vivienda_id;
    private String mensaje;
}
