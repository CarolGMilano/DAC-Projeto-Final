package br.net.dac.mscliente2.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.mscliente2.model.dto.ClienteAprovacaoDTO;
import br.net.dac.mscliente2.model.dto.ClienteInsercaoDTO;
import br.net.dac.mscliente2.model.dto.ClienteRejeicaoDTO;
import br.net.dac.mscliente2.model.dto.RejeicaoDTO;
import br.net.dac.mscliente2.model.entity.Cliente;
import br.net.dac.mscliente2.model.enums.StatusClienteEnum;
import br.net.dac.mscliente2.model.exception.CPFDuplicadoException;
import br.net.dac.mscliente2.model.exception.ClienteNaoEncontradoException;
import br.net.dac.mscliente2.model.exception.UsuarioDuplicadoException;
import br.net.dac.mscliente2.repository.ClienteRepository;

@Service
public class ClienteService {
  @Autowired
  private ClienteRepository clienteRepository;

  private void validarClienteInsercao(ClienteInsercaoDTO cliente)  {
    if (cliente.getIdUsuario() == null) {
      throw new IllegalArgumentException("Usuário não cadastrado");
    }

    if (cliente.getNome() == null || cliente.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
      throw new IllegalArgumentException("CPF é obrigatório");
    } else if (!cliente.getCpf().matches("\\d+")) {
      throw new IllegalArgumentException("O CPF deve conter apenas números");
    }

    if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
      throw new IllegalArgumentException("Telefone é obrigatório");
    } else if (!cliente.getTelefone().matches("\\d+")) {
      throw new IllegalArgumentException("O telefone deve conter apenas números");
    }
  }

  public ClienteInsercaoDTO inserirCliente(ClienteInsercaoDTO dto)  {
    validarClienteInsercao(dto);

    Cliente cpfExistente = clienteRepository.findByCpf(dto.getCpf());

    if (cpfExistente != null) {
      throw new CPFDuplicadoException();
    }

    Cliente usuarioExistente = clienteRepository.findByIdUsuario(dto.getIdUsuario());

    if (usuarioExistente != null) {
      throw new UsuarioDuplicadoException();
    }

    Cliente cliente = new Cliente();

    cliente.setIdUsuario(dto.getIdUsuario());
    cliente.setCpf(dto.getCpf());
    cliente.setNome(dto.getNome());
    cliente.setTelefone(dto.getTelefone());
    cliente.setSalario(dto.getSalario());
    cliente.setEndereco(dto.getEndereco());
    cliente.setCep(dto.getCep());
    cliente.setCidade(dto.getCidade());
    cliente.setEstado(dto.getEstado());
    cliente.setCpfGerente(dto.getCpfGerente());
    cliente.setAtivo(StatusClienteEnum.PENDENTE.name());

    Cliente clienteAdicionado = clienteRepository.save(cliente);

    return new ClienteInsercaoDTO(
      clienteAdicionado.getId(),
      clienteAdicionado.getIdUsuario(),
      clienteAdicionado.getCpf(),
      clienteAdicionado.getNome(),
      clienteAdicionado.getTelefone(),
      clienteAdicionado.getSalario(),
      clienteAdicionado.getEndereco(),
      clienteAdicionado.getCep(),
      clienteAdicionado.getCidade(),
      clienteAdicionado.getEstado(),
      clienteAdicionado.getCpfGerente(),
      clienteAdicionado.getAtivo()
    );
  }

  public ClienteInsercaoDTO consultarClientePorCPF(String cpfCliente) {
    Cliente clienteEncontrado = clienteRepository.findByCpf(cpfCliente);

    if (!StatusClienteEnum.ATIVO.name().equals(clienteEncontrado.getAtivo())) {
      throw new ClienteNaoEncontradoException();
    }

    return new ClienteInsercaoDTO(
      clienteEncontrado.getId(),
      clienteEncontrado.getIdUsuario(),
      clienteEncontrado.getCpf(),
      clienteEncontrado.getNome(),
      clienteEncontrado.getTelefone(),
      clienteEncontrado.getSalario(),
      clienteEncontrado.getEndereco(),
      clienteEncontrado.getCep(),
      clienteEncontrado.getCidade(),
      clienteEncontrado.getEstado(),
      clienteEncontrado.getCpfGerente(),
      clienteEncontrado.getAtivo()
    );
  }

  public List<ClienteInsercaoDTO> listarPendentes() {
    List<ClienteInsercaoDTO> clientes = new ArrayList<>();

    for (Cliente cliente : clienteRepository.findAll()) {
      if (StatusClienteEnum.PENDENTE.name().equals(cliente.getAtivo())) {
        clientes.add(
          new ClienteInsercaoDTO(
            cliente.getId(),
            cliente.getIdUsuario(),
            cliente.getCpf(),
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getSalario(),
            cliente.getEndereco(),
            cliente.getCep(),
            cliente.getCidade(),
            cliente.getEstado(),
            cliente.getCpfGerente(),
            cliente.getAtivo()
          )
        );
      }
    }

    return clientes;
  }

  public List<ClienteInsercaoDTO> listarAtivos() {
    List<ClienteInsercaoDTO> clientes = new ArrayList<>();

    for (Cliente cliente : clienteRepository.findAll()) {
      if (StatusClienteEnum.ATIVO.name().equals(cliente.getAtivo())) {
        clientes.add(
          new ClienteInsercaoDTO(
            cliente.getId(),
            cliente.getIdUsuario(),
            cliente.getCpf(),
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getSalario(),
            cliente.getEndereco(),
            cliente.getCep(),
            cliente.getCidade(),
            cliente.getEstado(),
            cliente.getCpfGerente(),
            cliente.getAtivo()
          )
        );
      }
    }

    return clientes;
  }

  public ClienteAprovacaoDTO aprovarCliente(String cpfCliente) {
    Cliente clienteEncontrado = clienteRepository.findByCpf(cpfCliente);

    if (clienteEncontrado == null) {
      throw new ClienteNaoEncontradoException();
    }

    clienteEncontrado.setAtivo(StatusClienteEnum.ATIVO.name());
    clienteEncontrado.setDataResposta(LocalDateTime.now());

    clienteRepository.save(clienteEncontrado);

    return new ClienteAprovacaoDTO(
      clienteEncontrado.getIdUsuario(),
      clienteEncontrado.getCpf(),      
      clienteEncontrado.getCpfGerente(),      
      clienteEncontrado.getSalario(),      
      clienteEncontrado.getAtivo()
    );
  }

  public ClienteRejeicaoDTO rejeitarCliente(String cpfCliente, RejeicaoDTO dto) {
    System.out.println("TESTE REJEITAR");
    Cliente clienteEncontrado = clienteRepository.findByCpf(cpfCliente);

    if (clienteEncontrado == null) {
      throw new ClienteNaoEncontradoException();
    }

    clienteEncontrado.setAtivo(StatusClienteEnum.REJEITADO.name());
    clienteEncontrado.setMotivoRejeicao(dto.getMotivo());
    clienteEncontrado.setDataResposta(LocalDateTime.now());

    clienteRepository.save(clienteEncontrado);

    return new ClienteRejeicaoDTO(
      clienteEncontrado.getIdUsuario(),
      clienteEncontrado.getCpf(),      
      clienteEncontrado.getAtivo()
    );
  }
}
