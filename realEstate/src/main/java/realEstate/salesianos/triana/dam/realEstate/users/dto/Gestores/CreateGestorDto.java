package realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores;

import lombok.*;
import realEstate.salesianos.triana.dam.realEstate.models.Inmobiliaria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGestorDto {

    private String username;
    private String avatar;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    private Long inmobiliariaId;
}
