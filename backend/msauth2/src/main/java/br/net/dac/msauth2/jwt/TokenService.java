package br.net.dac.msauth2.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.net.dac.msauth2.model.entity.Usuario;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(Usuario usuario) {
    try{
      Algorithm algoritmo = Algorithm.HMAC256(secret);

      String token = JWT.create()
        .withIssuer("msauth")
        .withSubject(usuario.getEmail())
        .withClaim("tipo", usuario.getTipo())
        .withExpiresAt(gerarTempo())
        .sign(algoritmo);

        return token;
    } catch(JWTCreationException e){
      throw new RuntimeException("Erro ao criar token", e);
    }
  }

  private Instant gerarTempo(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  } 

  public String validarToken(String token){
    try {
      Algorithm algoritmo = Algorithm.HMAC256(secret);

      return JWT.require(algoritmo)
                .withIssuer("msauth")
                .build()
                .verify(token)
                .getSubject();
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Token inválido ou expirado");
    }
  }
}
