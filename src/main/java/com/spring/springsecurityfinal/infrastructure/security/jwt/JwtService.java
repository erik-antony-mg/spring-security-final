package com.spring.springsecurityfinal.infrastructure.security.jwt;

import com.spring.springsecurityfinal.domain.entity.Usuario;
import com.spring.springsecurityfinal.infrastructure.security.jwt.exceptions.JwtExceptionCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.private.key}")
    private String keyPrivate;
    @Value("${jwt.duration.token}")
    private String durationToken;

    public String generateToken(Usuario usuario) {

        Date fechaActual = new Date(System.currentTimeMillis());

        long duracionTokenMillis = Long.parseLong(durationToken);
        Date fechaExpiracion = new Date(System.currentTimeMillis() + duracionTokenMillis);

        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .subject(usuario.getEmail())
                .issuedAt(fechaActual)
                .claim("fechaIniccio",sdf.format(fechaActual))
                .expiration(fechaExpiracion)
                .claim("fechaExpiracion",sdf.format(fechaExpiracion))
                .claim("roles",usuario.getRoles())
                .signWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()),Jwts.SIG.HS256)
                .compact();
    }


    public Boolean validarToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()))
                    .build()
                    .parseSignedClaims(token);

            Claims claims = jws.getPayload();

            Date now = new Date();
            if (claims.getExpiration().before(now)) {
                throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT ha caducado");
            }
            return true;
        } catch (SignatureException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "Error de firma en el token JWT");
        } catch (MalformedJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT está mal formado");
        } catch (ExpiredJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT ha caducado");
        } catch (UnsupportedJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El tipo de token JWT no es compatible");
        } catch (IllegalArgumentException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El cuerpo del token JWT está vacío");
        }
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String getEmail(String token){
        return getClaim(token, Claims::getSubject);
    }
}