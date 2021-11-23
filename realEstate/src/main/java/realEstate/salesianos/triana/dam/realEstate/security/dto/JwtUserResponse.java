package realEstate.salesianos.triana.dam.realEstate.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String nombre;
    private String avatar;
    private String rol;
    private String token;
}
