package br.net.dac.msauth2.service;

//Importações para criptografia das senhas
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msauth2.model.dto.RebootResponseDTO;
import br.net.dac.msauth2.model.entity.Usuario;
import br.net.dac.msauth2.model.enums.StatusUsuarioEnum;
import br.net.dac.msauth2.model.enums.TipoUsuarioEnum;
import br.net.dac.msauth2.repository.UsuarioRepository;

@Service
public class RebootService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  private String gerarSalt() {
    //Cria um array de bytes
    byte[] salt = new byte[16];
    //O SecureRandom é uma maneira menos previsível de gerar números aleatórios
    new SecureRandom().nextBytes(salt);

    //Pega o salt gerado, transforma os bytes em uma string legível e retorna.
    return Base64.getEncoder().encodeToString(salt);
  }

  //Cria a senha criptografada
  private String gerarHash(String senha, String salt) throws NoSuchAlgorithmException {
    //Junta a senha sem criptografia com a salt criada
    String senhaConcatenada = senha + salt;

    //Aqui que fica o algoritmo do SHA-256
    MessageDigest algoritmoSHA256 = MessageDigest.getInstance("SHA-256");

    //Converte em bytes, seguindo o UTF-8, e depois calcula o hash com o algorítimo SHA-256 do Java
    byte[] hashEmBytes = algoritmoSHA256.digest(senhaConcatenada.getBytes(StandardCharsets.UTF_8));

    //Maneira eficiênte de criar uma string concatenada.
    StringBuilder hashHexa = new StringBuilder();

    for (byte b : hashEmBytes) {
      //Aqui ele vai converter cada byte em hexadecimal de 2 dígitos
      hashHexa.append(String.format("%02x", b));
    }

    //Retorna esse hexadecimal da senha + salt. Essa é a senha criptografada, ela não poderá ser revertida.
    return hashHexa.toString();
  }

  public List<RebootResponseDTO> reboot() throws Exception {
    List<RebootResponseDTO> usuariosReboot = new ArrayList<>();

    usuarioRepository.deleteAll();

    //ADMIN
    usuariosReboot.add(inserirUsuario("adm1@bantads.com.br", "tads", TipoUsuarioEnum.ADMINISTRADOR.name()));

    //GERENTE
    usuariosReboot.add(inserirUsuario("ger1@bantads.com.br", "tads", TipoUsuarioEnum.GERENTE.name()));
    usuariosReboot.add(inserirUsuario("ger2@bantads.com.br", "tads", TipoUsuarioEnum.GERENTE.name()));
    usuariosReboot.add(inserirUsuario("ger3@bantads.com.br", "tads", TipoUsuarioEnum.GERENTE.name()));

    //CLIENTE
    usuariosReboot.add(inserirUsuario("cli1@bantads.com.br", "tads", TipoUsuarioEnum.CLIENTE.name()));
    usuariosReboot.add(inserirUsuario("cli2@bantads.com.br", "tads", TipoUsuarioEnum.CLIENTE.name()));
    usuariosReboot.add(inserirUsuario("cli3@bantads.com.br", "tads", TipoUsuarioEnum.CLIENTE.name()));
    usuariosReboot.add(inserirUsuario("cli4@bantads.com.br", "tads", TipoUsuarioEnum.CLIENTE.name()));
    usuariosReboot.add(inserirUsuario("cli5@bantads.com.br", "tads", TipoUsuarioEnum.CLIENTE.name()));

    return usuariosReboot;
  }

  public RebootResponseDTO inserirUsuario(String email, String senha, String tipo) throws Exception {
    Usuario u = new Usuario();

    u.setEmail(email);

    String salt = gerarSalt();
    String senhaHash;

    senhaHash = gerarHash(senha, salt);

    u.setSenha(senhaHash);
    u.setSalt(salt);
    u.setTipo(tipo);
    u.setAtivo(StatusUsuarioEnum.ATIVO.name());

    usuarioRepository.save(u);

    return new RebootResponseDTO(
      u.getId(),
      u.getEmail()
    );
  }
}
