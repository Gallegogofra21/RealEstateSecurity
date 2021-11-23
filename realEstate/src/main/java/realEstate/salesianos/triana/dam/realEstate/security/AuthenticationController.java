package realEstate.salesianos.triana.dam.realEstate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import realEstate.salesianos.triana.dam.realEstate.security.dto.JwtUserResponse;
import realEstate.salesianos.triana.dam.realEstate.security.dto.LoginDto;
import realEstate.salesianos.triana.dam.realEstate.security.jwt.JwtProvider;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<?> quienSoyYo(@AuthenticationPrincipal Usuario user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }

    private JwtUserResponse convertUserToJwtUserResponse(Usuario user, String jwt) {
        return JwtUserResponse.builder()
                .nombre(user.getNombre())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .rol(user.getRol().name())
                .token(jwt)
                .build();
    }


}
