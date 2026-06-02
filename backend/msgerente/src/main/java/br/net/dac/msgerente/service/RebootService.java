package br.net.dac.msgerente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msgerente.model.Gerente;
import br.net.dac.msgerente.model.dto.RebootResponseDTO;
import br.net.dac.msgerente.model.enums.StatusGerenteEnum;
import br.net.dac.msgerente.repository.GerenteRepository;

@Service
public class RebootService {
  @Autowired
  private GerenteRepository gerenteRepository;

  public void reboot(List<RebootResponseDTO> usuarios) {
    gerenteRepository.deleteAll();

    inserirGerente(usuarios, "98574307084", "Geniéve", "ger1@bantads.com.br");
    inserirGerente(usuarios, "64065268052", "Godophredo", "ger2@bantads.com.br");
    inserirGerente(usuarios, "23862179060", "Gyândula", "ger3@bantads.com.br");
    inserirGerente(usuarios, "40501740066", "Adamântio", "adm1@bantads.com.br");
  }

  public void inserirGerente(List<RebootResponseDTO> usuarios, String cpf, String nome, String email) {
    Gerente g = new Gerente();

    String idUsuario = null;

    for (RebootResponseDTO u : usuarios) {
      if (u.getEmail().equals(email)) {
        idUsuario = u.getId();

        break;
      }
    }

    g.setIdUsuario(idUsuario);
    g.setCpf(cpf);
    g.setNome(nome);
    g.setAtivo(StatusGerenteEnum.ATIVO.name());

    gerenteRepository.save(g);
  }
}
