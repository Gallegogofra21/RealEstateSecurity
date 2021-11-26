package realEstate.salesianos.triana.dam.realEstate.users.dto.Gestores;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetGestorDto {

    private String email;
    private String rol;
    private String userName;
    private String direccion;
    private String telefono;
    private Long inmobiliariaId;
}
