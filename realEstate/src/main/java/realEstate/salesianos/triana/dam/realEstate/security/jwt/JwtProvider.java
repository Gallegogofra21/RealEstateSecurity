package realEstate.salesianos.triana.dam.realEstate.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Log
@Service
public class JwtProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    @Value("${jwt.secret:DSjdhgsjdhgsjdhgjghdsjugbsdngjdshgj}")
    private String jwtSecret;

    @Value("${jwt.duration:86400}")
    private int jwtLifeInSeconds;

    private JwtParser parser;

    @PostConstruct
    public void init(){
        parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()  ;
    }

    public String generateToken(Authentication authentication) {

        Usuario user = (Usuario) authentication.getPrincipal();

        Date tokenExpirationDate = Date.from(LocalDateTime
                .now()
                .plusSeconds(jwtLifeInSeconds)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(tokenExpirationDate)
                .claim("nombre", user.getNombre())
                .claim("rol", user.getRol().name())
                .compact();
    }

    public Long getUserIdFromJwt(String token) {
        return Long.valueOf(parser.parseClaimsJws(token).getBody().getSubject());
    }

    /*public UUID getUserIdFromJwt(String token) {
        return UUID.fromString(parser.parseClaimsJwt(token).getBody().getSubject();
    }*/

    public boolean validateToken (String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex) {
            log.info("Token malformado: " + ex.getMessage());
        }catch (ExpiredJwtException ex) {
            log.info("El token ha expirado: " + ex.getMessage());
        }catch (UnsupportedJwtException ex) {
            log.info("Token JWT no soportado: " + ex.getMessage());
        }catch (IllegalArgumentException ex) {
            log.info("JWT claims vacío");
        }
        return false;
    }
}
