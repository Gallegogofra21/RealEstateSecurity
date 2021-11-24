package realEstate.salesianos.triana.dam.realEstate.users.dto.Propietarios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePropietarioDto {

    private String username;
    private String avatar;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    private Long inmobiliariaId;
}
