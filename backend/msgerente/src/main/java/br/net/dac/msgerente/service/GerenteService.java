package br.net.dac.msgerente.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msgerente.model.Gerente;
import br.net.dac.msgerente.model.dto.GerenteDTO;
import br.net.dac.msgerente.model.dto.GerenteResumoDTO;
import br.net.dac.msgerente.model.exception.CPFDuplicadoException;
import br.net.dac.msgerente.model.exception.GerenteNaoEncontradoException;
import br.net.dac.msgerente.model.exception.UsuarioDuplicadoException;
import br.net.dac.msgerente.repository.GerenteRepository;

@Service
public class GerenteService {
  @Autowired
  private GerenteRepository gerenteRepository;

  private void validarGerenteInsercao(GerenteDTO gerente)  {
    if (gerente.getIdUsuario() == null) {
      throw new IllegalArgumentException("Usuário não cadastrado");
    }

    if (gerente.getNome() == null || gerente.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    if (gerente.getCpf() == null || gerente.getCpf().isBlank()) {
      throw new IllegalArgumentException("CPF é obrigatório");
    } else if (!gerente.getCpf().matches("\\d+")) {
      throw new IllegalArgumentException("O CPF deve conter apenas números");
    }

    if (gerente.getTelefone() == null || gerente.getTelefone().isBlank()) {
      throw new IllegalArgumentException("Telefone é obrigatório");
    } else if (!gerente.getTelefone().matches("\\d+")) {
      throw new IllegalArgumentException("O telefone deve conter apenas números");
    }
  }

  private void validarGerenteAlteracao(GerenteDTO gerente)  {
    if (gerente.getNome() == null || gerente.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }
  }

  public GerenteDTO inserirGerente(GerenteDTO dto)  {
    validarGerenteInsercao(dto);

    Gerente cpfExistente = gerenteRepository.findByCpf(dto.getCpf());

    if (cpfExistente != null) {
        throw new CPFDuplicadoException();
    }

    Gerente usuarioExistente = gerenteRepository.findByIdUsuario(dto.getIdUsuario());

    if (usuarioExistente != null) {
        throw new UsuarioDuplicadoException();
    }

    Gerente gerente = new Gerente();

    gerente.setIdUsuario(dto.getIdUsuario());
    gerente.setCpf(dto.getCpf());
    gerente.setNome(dto.getNome());
    gerente.setTelefone(dto.getTelefone());
    gerente.setAtivo(true);

    Gerente gerenteAdicionado = gerenteRepository.save(gerente);

    return new GerenteDTO(
      gerenteAdicionado.getId(),
      gerenteAdicionado.getIdUsuario(),
      gerenteAdicionado.getCpf(),
      gerenteAdicionado.getNome(),
      gerenteAdicionado.getTelefone(),
      gerenteAdicionado.getAtivo()
    );
  }

  public GerenteDTO consultarGerentePorId(Long idGerente) {
    //O findById retorna um Optional<Gerente> que pode ser nulo e pra evitar um NullPointerException ele te obriga a tratar a saída.
    Gerente gerenteEncontrado = gerenteRepository.findById(idGerente).orElseThrow(GerenteNaoEncontradoException::new);

    if (!gerenteEncontrado.getAtivo()) {
      throw new GerenteNaoEncontradoException();
    }

    return new GerenteDTO(
      gerenteEncontrado.getId(),
      gerenteEncontrado.getIdUsuario(),
      gerenteEncontrado.getCpf(),
      gerenteEncontrado.getNome(),
      gerenteEncontrado.getTelefone(),
      gerenteEncontrado.getAtivo()
    );
  }

  public Long consultarGerenteIdUsuario(Long idGerente) {
    Gerente gerenteEncontrado = gerenteRepository.findById(idGerente).orElseThrow(GerenteNaoEncontradoException::new);

    if (!gerenteEncontrado.getAtivo()) {
      throw new GerenteNaoEncontradoException();
    }

    return gerenteEncontrado.getIdUsuario();
  }

  public GerenteDTO consultarGerentePorCPF(String cpfGerente) {
    Gerente gerenteEncontrado = gerenteRepository.findByCpfAndAtivoTrue(cpfGerente);

    if(gerenteEncontrado == null){
      throw new GerenteNaoEncontradoException();
    }

    return new GerenteDTO(
      gerenteEncontrado.getId(),
      gerenteEncontrado.getIdUsuario(),
      gerenteEncontrado.getCpf(),
      gerenteEncontrado.getNome(),
      gerenteEncontrado.getTelefone(),
      gerenteEncontrado.getAtivo()
    );
  }

  public List<GerenteResumoDTO> listarGerentes() {
    List<GerenteResumoDTO> gerentes = new ArrayList<>();

    for (Gerente gerente : gerenteRepository.findByAtivoTrue()) {
      gerentes.add(
        new GerenteResumoDTO(
          gerente.getIdUsuario(),
          gerente.getCpf(),
          gerente.getNome(),
          gerente.getTelefone()
        )
      );
    }

    return gerentes;
  }

  public void desativarGerente (String cpfGerente) {
    Gerente gerenteEncontrado = gerenteRepository.findByCpfAndAtivoTrue(cpfGerente);
    
    if (gerenteEncontrado == null) {
      throw new GerenteNaoEncontradoException();
    }

    gerenteEncontrado.setAtivo(false);
    gerenteRepository.save(gerenteEncontrado);
  }

  public GerenteDTO alterarGerente(GerenteDTO dto) {
    validarGerenteAlteracao(dto);
    
    Gerente gerenteEncontrado = gerenteRepository.findByCpfAndAtivoTrue(dto.getCpf());

    if (gerenteEncontrado == null) {
      throw new GerenteNaoEncontradoException();
    }

    gerenteEncontrado.setNome(dto.getNome());

    Gerente gerenteAlterado = gerenteRepository.save(gerenteEncontrado);

    return new GerenteDTO(
      gerenteAlterado.getId(),
      gerenteAlterado.getIdUsuario(),
      gerenteAlterado.getCpf(),
      gerenteAlterado.getNome(),
      gerenteAlterado.getTelefone(),
      gerenteAlterado.getAtivo()
    );
  }
}