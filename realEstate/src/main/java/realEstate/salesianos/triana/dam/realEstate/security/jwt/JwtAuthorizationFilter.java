package realEstate.salesianos.triana.dam.realEstate.security.jwt;

import antlr.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
import realEstate.salesianos.triana.dam.realEstate.users.services.UserEntityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserEntityService usuarioService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        try {
            if(StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
                Long userId = jwtProvider.getUserIdFromJwt(token);

                Option<Usuario> usuario = usuarioService.findById(userId);

                if(usuario.isPresent()){
                    Usuario user = usuario.get();
                    UsernamePasswordAuthenticationToken authenticacion =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    user.getRol(),
                                    user.getAuthorities()
                            );
                    authenticacion.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticacion);
                }
            }
        }
    }
}
