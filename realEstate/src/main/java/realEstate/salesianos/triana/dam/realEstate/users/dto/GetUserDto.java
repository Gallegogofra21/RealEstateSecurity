package realEstate.salesianos.triana.dam.realEstate.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String nombre;
    private String email;
    private String role;
}
