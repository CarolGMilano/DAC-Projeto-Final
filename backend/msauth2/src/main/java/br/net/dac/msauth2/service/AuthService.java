package br.net.dac.msauth2.service;

//Importações para criptografia das senhas
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msauth2.jwt.TokenService;
import br.net.dac.msauth2.model.dto.LoginDTO;
import br.net.dac.msauth2.model.dto.RejeicaoDTO;
import br.net.dac.msauth2.model.dto.RespostaDTO;
import br.net.dac.msauth2.model.dto.UsuarioAlteracaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioAprovacaoRejeicaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioCriacaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioDesativacaoDTO;
import br.net.dac.msauth2.model.entity.Usuario;
import br.net.dac.msauth2.model.enums.StatusUsuarioEnum;
import br.net.dac.msauth2.model.enums.TipoUsuarioEnum;
import br.net.dac.msauth2.model.exception.EmailDuplicadoException;
import br.net.dac.msauth2.model.exception.SenhaIncorretaException;
import br.net.dac.msauth2.model.exception.UsuarioNaoEncontradoException;
import br.net.dac.msauth2.repository.UsuarioRepository;

@Service
public class AuthService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private EmailService emailService;

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

  public Map<String, Object> login(LoginDTO login) throws Exception {
    Usuario usuarioEncontrado = usuarioRepository.findByEmail(login.getLogin()).orElseThrow(() -> new UsuarioNaoEncontradoException());

    String hashInformado = gerarHash(login.getSenha(), usuarioEncontrado.getSalt());

    if (!usuarioEncontrado.getSenha().equals(hashInformado)) {
      throw new SenhaIncorretaException();
    }

    if (!StatusUsuarioEnum.ATIVO.name().equals(usuarioEncontrado.getAtivo())) {
      System.out.println("Falou aqui no ativo");
      throw new UsuarioNaoEncontradoException();
    }

    TipoUsuarioEnum tipo = TipoUsuarioEnum.valueOf(usuarioEncontrado.getTipo());
    String token = tokenService.gerarToken(usuarioEncontrado);
    
    Map<String, Object> resposta = new HashMap<>();

    resposta.put("access_token", token);
    resposta.put("token_type", "bearer");
    resposta.put("tipo", tipo.name());
    resposta.put("usuario", new RespostaDTO(
      usuarioEncontrado.getId(),
      usuarioEncontrado.getEmail(),
      tipo.name()
    ));

    return resposta;
  }

  public RespostaDTO criarUsuario(UsuarioCriacaoDTO usuarioDTO) throws Exception {
    if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
      throw new EmailDuplicadoException(usuarioDTO.getEmail());
    }

    String salt = gerarSalt();
    String senhaHash;
    String senhaAleatoria = null;

    TipoUsuarioEnum tipo = TipoUsuarioEnum.valueOf(usuarioDTO.getTipo());
    
    if (tipo == TipoUsuarioEnum.CLIENTE) {
      senhaAleatoria = Long.toString(Math.abs(new java.util.Random().nextLong()), 36).substring(0, 4);
      senhaHash = gerarHash(senhaAleatoria, salt);
    } else {
      senhaHash = gerarHash(usuarioDTO.getSenha(), salt);
    }

    StatusUsuarioEnum status;

    if (tipo == TipoUsuarioEnum.CLIENTE) {
      status = StatusUsuarioEnum.PENDENTE;
    } else {
      status = StatusUsuarioEnum.ATIVO;
    }

    Usuario usuario = new Usuario();

    usuario.setEmail(usuarioDTO.getEmail());
    usuario.setSenha(senhaHash);
    usuario.setSalt(salt);

    usuario.setTipo(tipo.name());
    usuario.setAtivo(status.name());

    Usuario usuarioSalvo = usuarioRepository.save(usuario);

    System.out.println(senhaAleatoria);

    if(tipo == TipoUsuarioEnum.CLIENTE){
      String mensagem = """
      Olá, %s!

      Recebemos sua solicitação de cadastro no BanTADS.

      Seu perfil foi encaminhado para análise pela nossa equipe. Assim que o processo for concluído, você receberá uma nova notificação por e-mail informando o resultado.

      Obrigado pelo interesse em fazer parte do nosso banco!

      Atenciosamente,
      Equipe BanTADS
      """.formatted(
        usuario.getEmail()
      );

      emailService.enviarEmail(
        "BanTADS - Cadastro recebido para análise",
        mensagem,
        //Mudar aqui na defesa
        "carolinamilano@ufpr.br"
      );
    }

    return new RespostaDTO(
      usuarioSalvo.getId(),
      usuarioSalvo.getEmail(),
      tipo.name()
    );
  }

  public RespostaDTO atualizarUsuario(UsuarioAlteracaoDTO usuarioDTO) throws Exception {
    Usuario usuario = usuarioRepository.findById(usuarioDTO.getId()).orElseThrow(() -> new UsuarioNaoEncontradoException());

    if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
      throw new UsuarioNaoEncontradoException();
    }

    if (usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().isBlank()) {
      Usuario usuarioComMesmoEmail = usuarioRepository.findByEmail(usuarioDTO.getEmail()).orElse(null);

      if (usuarioComMesmoEmail != null && !usuarioComMesmoEmail.getId().equals(usuario.getId())) {
        throw new EmailDuplicadoException(usuarioDTO.getEmail());
      }

      usuario.setEmail(usuarioDTO.getEmail());
    }

    if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isBlank()) {
      String novoSalt = gerarSalt();
      String novaSenhaHash = gerarHash(usuarioDTO.getSenha(), novoSalt);

      usuario.setSenha(novaSenhaHash);
      usuario.setSalt(novoSalt);
    }

    Usuario usuarioAtualizado = usuarioRepository.save(usuario);

    return new RespostaDTO(
      usuarioAtualizado.getId(),
      usuarioAtualizado.getEmail(),
      TipoUsuarioEnum.valueOf(usuario.getTipo()).name()
    );
  }

  public RespostaDTO desativarUsuario(UsuarioDesativacaoDTO usuarioDTO) throws Exception {
    Usuario usuario = usuarioRepository.findById(usuarioDTO.getId()).orElseThrow(() -> new UsuarioNaoEncontradoException());

    if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
      throw new UsuarioNaoEncontradoException();
    }

    usuario.setAtivo(StatusUsuarioEnum.INATIVO.name());

    Usuario usuarioAtualizado = usuarioRepository.save(usuario);

    return new RespostaDTO(
      usuarioAtualizado.getId(),
      usuarioAtualizado.getEmail(),
      TipoUsuarioEnum.valueOf(usuario.getTipo()).name()
    );
  }

  //Lógica de aprovação
  public UsuarioAprovacaoRejeicaoDTO aprovarCliente(String id) throws Exception {
    Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);

    String salt = gerarSalt();
    String senhaHash;
    String senhaAleatoria = null;

    senhaAleatoria = Long.toString(Math.abs(new java.util.Random().nextLong()), 36).substring(0, 4);
    senhaHash = gerarHash(senhaAleatoria, salt);

    usuarioEncontrado.setSalt(salt);
    usuarioEncontrado.setSenha(senhaHash);
    usuarioEncontrado.setAtivo(StatusUsuarioEnum.ATIVO.name());

    if(TipoUsuarioEnum.CLIENTE.name().equals(usuarioEncontrado.getTipo())){
      String mensagem = """
        Olá, %s!

        Temos uma ótima notícia: seu cadastro foi APROVADO!

        Sua conta já está disponível para acesso ao sistema BanTADS.

        Dados de acesso:

        Login: %s
        Senha: %s

        Obrigado por escolher o BanTADS!

        Atenciosamente,
        Equipe BanTADS
        """.formatted(
            usuarioEncontrado.getEmail(),
            usuarioEncontrado.getEmail(),
            senhaAleatoria
        );

      emailService.enviarEmail(
        "Bem-vindo ao BanTADS",
        mensagem,
        //Mudar aqui na defesa
        "carolinamilano@ufpr.br"
      );
    }

    usuarioRepository.save(usuarioEncontrado);

    return new UsuarioAprovacaoRejeicaoDTO(
      usuarioEncontrado.getId()
    );
  }

  //Lógica de rejeição
  public UsuarioAprovacaoRejeicaoDTO rejeitarCliente(String id, RejeicaoDTO dto) throws Exception {
    Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);

    usuarioEncontrado.setAtivo(StatusUsuarioEnum.REJEITADO.name());

    if(TipoUsuarioEnum.CLIENTE.name().equals(usuarioEncontrado.getTipo())){
      String mensagem = """
        Olá, %s!

        Finalizamos a análise da sua solicitação de cadastro no BanTADS.

        Infelizmente, sua solicitação não foi aprovada no momento.

        Motivo da decisão:
        %s

        Se tiver dúvidas ou precisar de informações adicionais, entre em contato com nossa equipe de suporte.

        Agradecemos seu interesse e o tempo dedicado ao processo.

        Atenciosamente,
        Equipe BanTADS
        """.formatted(
            usuarioEncontrado.getEmail(),
            dto.getMotivo()
        );

      emailService.enviarEmail(
        "BanTADS - Atualização da sua solicitação de cadastro",
        mensagem,
        //Mudar aqui na defesa
        "carolinamilano@ufpr.br"
      );
    }

    usuarioRepository.save(usuarioEncontrado);

    return new UsuarioAprovacaoRejeicaoDTO(
      usuarioEncontrado.getId()
    );
  }

  public RespostaDTO buscarPorId(String id) throws Exception {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());

    if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
      throw new UsuarioNaoEncontradoException();
    }

    return new RespostaDTO(
      usuario.getId(),
      usuario.getEmail(),
      TipoUsuarioEnum.valueOf(usuario.getTipo()).name()
    );
  }

  public RespostaDTO buscarPorEmail(String email) throws Exception {
    Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException());

    if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
      throw new UsuarioNaoEncontradoException();
    }

    return new RespostaDTO(
      usuario.getId(),
      usuario.getEmail(),
      TipoUsuarioEnum.valueOf(usuario.getTipo()).name()
    );
  }

  public List<RespostaDTO> listarUsuarios(List<TipoUsuarioEnum> tipos) {
    List<Usuario> usuarios = usuarioRepository.findAll();
    List<RespostaDTO> resposta = new ArrayList<>();

    for (Usuario usuario : usuarios) {
      if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
        continue;
      }

      TipoUsuarioEnum tipo = TipoUsuarioEnum.valueOf(usuario.getTipo());

      if (!tipos.contains(tipo)) {
        continue;
      }

      RespostaDTO dto = new RespostaDTO();
      dto.setId(usuario.getId());
      dto.setEmail(usuario.getEmail());
      dto.setTipo(tipo.name());

      resposta.add(dto);
    }

    return resposta;
  }

  public List<RespostaDTO> listarPendentes() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    List<RespostaDTO> resposta = new ArrayList<>();

    for (Usuario usuario : usuarios) {
      if (!StatusUsuarioEnum.PENDENTE.name().equals(usuario.getAtivo())) {
        continue;
      }

      if (!TipoUsuarioEnum.CLIENTE.name().equals(usuario.getTipo())) {
        continue;
      }

      RespostaDTO dto = new RespostaDTO();
      dto.setId(usuario.getId());
      dto.setEmail(usuario.getEmail());
      dto.setTipo(usuario.getTipo());

      resposta.add(dto);
    }

    return resposta;
  }

  public List<RespostaDTO> listarClientes() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    List<RespostaDTO> resposta = new ArrayList<>();

    for (Usuario usuario : usuarios) {
      if (!StatusUsuarioEnum.ATIVO.name().equals(usuario.getAtivo())) {
        continue;
      }

      if (!TipoUsuarioEnum.CLIENTE.name().equals(usuario.getTipo())) {
        continue;
      }

      RespostaDTO dto = new RespostaDTO();
      dto.setId(usuario.getId());
      dto.setEmail(usuario.getEmail());
      dto.setTipo(usuario.getTipo());

      resposta.add(dto);
    }

    return resposta;
  }

  public void rollback(UsuarioDesativacaoDTO usuarioDTO) throws Exception {
    if (!usuarioRepository.existsById(usuarioDTO.getId())) {
      throw new UsuarioNaoEncontradoException();
    }

    usuarioRepository.deleteById(usuarioDTO.getId());
  }
}