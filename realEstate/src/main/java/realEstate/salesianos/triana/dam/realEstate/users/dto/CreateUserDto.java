package realEstate.salesianos.triana.dam.realEstate.users.dto;

import lombok.*;
import realEstate.salesianos.triana.dam.realEstate.models.Inmobiliaria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String username;
    private String avatar;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    //private int numViviendas;
    private Long inmobiliariaId;

}
