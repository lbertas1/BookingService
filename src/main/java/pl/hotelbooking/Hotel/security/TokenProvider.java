package pl.hotelbooking.Hotel.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.hotelbooking.Hotel.domain.UserPrincipal;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {

    public String generateToken(UserPrincipal userPrincipal) {
        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withSubject(userPrincipal.getUsername())
                .withClaim(SecurityConstants.CLAIM, userPrincipal.getAuthorities().toString())
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getBytes()));
    }

    public boolean isTokenValid(String username, String token) {
        return !username.isBlank() && isTokenExpired(getJWTVerifier(), token);
    }

    public boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiration = jwtVerifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public String getSubject(String token) {
        return getJWTVerifier().verify(token).getSubject();
    }

    public List<GrantedAuthority> getAuthority(String token) {
        return Collections.singletonList(new SimpleGrantedAuthority(getClaimsFromToken(token)));
    }

    private String getClaimsFromToken(String token) {
        return getJWTVerifier().verify(token).getClaim(SecurityConstants.CLAIM).asString();
    }

    private JWTVerifier getJWTVerifier() {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.SECRET_KEY);
            return JWT.require(algorithm).build();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token cannot be verify");
        }
    }
}
