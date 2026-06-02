package br.net.dac.mscliente2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.mscliente2.model.dto.RebootResponseDTO;
import br.net.dac.mscliente2.model.entity.Cliente;
import br.net.dac.mscliente2.model.enums.StatusClienteEnum;
import br.net.dac.mscliente2.repository.ClienteRepository;

@Service
public class RebootService {
  @Autowired
  private ClienteRepository clienteRepository;

  public void reboot(List<RebootResponseDTO> usuarios) {
    clienteRepository.deleteAll();

    inserirCliente(
      usuarios, 
      "12912861012", 
      "Catharyna", 
      "cli1@bantads.com.br", 
      "41911111111", 
      BigDecimal.valueOf(10000), 
      "Rua XV de Novembro, 100", 
      "80010000", 
      "Curitiba", 
      "PR", 
      "98574307084"
    );
    inserirCliente(
      usuarios, 
      "09506382000", 
      "Cleuddônio", 
      "cli2@bantads.com.br",
      "41922222222",
      BigDecimal.valueOf(20000),
      "Praça da Sé, 1", 
      "01001000", 
      "São Paulo", 
      "SP",
      "64065268052"
    );
    inserirCliente(
      usuarios, 
      "85733854057", 
      "Catianna", 
      "cli3@bantads.com.br",
      "41933333333",
      BigDecimal.valueOf(3000),
      "Avenida Rio Branco, 185", 
      "20040020", 
      "Rio de Janeiro", 
      "RJ",
      "23862179060"
    );
    inserirCliente(
      usuarios, 
      "58872160006", 
      "Cutardo", 
      "cli4@bantads.com.br",
      "41944444444",
      BigDecimal.valueOf(500),
      "Avenida Afonso Pena, 1500", 
      "30112000", 
      "Belo Horizonte", 
      "MG",
      "98574307084"
    );
    inserirCliente(
      usuarios, 
      "76179646090", 
      "Coândrya", 
      "cli5@bantads.com.br",
      "41955555555",
      BigDecimal.valueOf(1500),
      "Rua Chile, 23", 
      "40070110", 
      "Salvador", 
      "BA",
      "64065268052"
    );
  }

  public void inserirCliente(
    List<RebootResponseDTO> usuarios, 
    String cpf, 
    String nome, 
    String email, 
    String telefone, 
    BigDecimal salario, 
    String endereco, 
    String cep, 
    String cidade, 
    String estado, 
    String cpfGerente
  ) {
    Cliente c = new Cliente();

    String idUsuario = null;

    for (RebootResponseDTO u : usuarios) {
      if (u.getEmail().equals(email)) {
        idUsuario = u.getId();

        break;
      }
    }

    c.setIdUsuario(idUsuario);
    c.setCpf(cpf);
    c.setNome(nome);
    c.setTelefone(telefone);
    c.setSalario(salario);
    c.setEndereco(endereco);
    c.setCep(cep);
    c.setCidade(cidade);
    c.setEstado(estado);
    c.setCpfGerente(cpfGerente);
    c.setDataResposta(LocalDateTime.now());
    c.setAtivo(StatusClienteEnum.ATIVO.name());

    clienteRepository.save(c);
  }
}

