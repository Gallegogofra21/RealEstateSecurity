package realEstate.salesianos.triana.dam.realEstate.users.dto;

import lombok.*;

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

}
