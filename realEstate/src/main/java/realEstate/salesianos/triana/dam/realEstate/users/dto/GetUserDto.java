package realEstate.salesianos.triana.dam.realEstate.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private Long id;
    private String avatar;
    private String nombre;
    private String email;
    private String role;
    //private int numViviendas;
    private Long inmobiliariaId;
}
