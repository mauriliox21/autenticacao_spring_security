package com.maurilio.autenticacao.security;

import com.maurilio.autenticacao.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenUtil {

    private static final String EMISSOR = "mauriliox21";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "01234567890123456789012345678901";
    private static final long ONE_SECONDE = 1000;
    private static final long ONE_MINUTE = 60*ONE_SECONDE;

    public static AuthToken encodeToken(Usuario usuario) {
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJWT = Jwts.builder().setSubject(usuario.getLogin())
                                        .setIssuer(EMISSOR)
                                        .setExpiration(new Date(System.currentTimeMillis() + ONE_MINUTE))
                                        .signWith(secretKey, SignatureAlgorithm.HS256)
                                        .compact();

        AuthToken token = new AuthToken(TOKEN_HEADER + tokenJWT);
        return token;
    }

    public static Authentication decodeToken(HttpServletRequest request){
        try{
            String tokenJwt = request.getHeader("Authorization");
            tokenJwt = tokenJwt.replace(TOKEN_HEADER, "");

            Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(TOKEN_KEY.getBytes()).build().parseClaimsJws(tokenJwt);

            String usuario = jwsClaims.getBody().getSubject();
            String emissor = jwsClaims.getBody().getIssuer();
            Date validate = jwsClaims.getBody().getExpiration();
            System.out.println(usuario);
            if(usuario.length() > 0 && emissor.equals(EMISSOR) && validate.after(new Date(System.currentTimeMillis()))){
                return new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
            }
        }
        catch (RuntimeException erro){
            System.out.println("Token invalido");
        }

        return null;
    }
}
