package uz.yengilyechim.rolepermission.security;

import io.jsonwebtoken.*;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value(value = "${accessTokenLifeTime}")
    private Long accessTokenLifeTime;
    //
    @Value(value = "${refreshTokenLifeTime}")
    private Long refreshTokenLifeTime;
    //
    @Value(value = "${tokenSecretKey}")
    private String tokenSecretKey;

    public String generateTokenFromId(UUID id, boolean isAccess) {
        Date expiredDate = new Date(System.currentTimeMillis() + (isAccess ? accessTokenLifeTime : refreshTokenLifeTime));
        return "Bearer " + Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecretKey)
                .compact();
    }
    public String generateTokenFromIdAndRoles(UUID id, Role role , boolean isAccess) {
        Date expiredDate = new Date(System.currentTimeMillis() + (isAccess ? accessTokenLifeTime : refreshTokenLifeTime));
        return "Bearer " + Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .claim("roles",role.getName())
                .setSubject(id.toString())
                .signWith(SignatureAlgorithm.HS512, tokenSecretKey)
                .compact();
    }

    public String getIdFromToken(String token){
        try {
           return Jwts
                    .parser()
                    .setSigningKey(tokenSecretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (Exception e) {
            throw new RestException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
    public Claims getClaimsFromToken(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(tokenSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (Exception e) {
            throw new RestException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void validToken(String token) {
            Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
    }

}
