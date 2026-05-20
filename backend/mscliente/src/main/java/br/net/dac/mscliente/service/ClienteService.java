package br.net.dac.mscliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.mscliente.model.dto.ClienteDTO;
import br.net.dac.mscliente.model.dto.EnderecoDTO;
import br.net.dac.mscliente.model.entity.ClienteEntity;
import br.net.dac.mscliente.model.entity.EnderecoEntity;
import br.net.dac.mscliente.model.entity.StatusCliente; 
import br.net.dac.mscliente.repository.ClienteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    
    public ClienteDTO cadastrarCliente(ClienteDTO dto) {
        if (clienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("Cliente com CPF " + dto.getCpf() + " já cadastrado.");
        }

        ClienteEntity entity = toEntity(dto);
        entity.setStatus(StatusCliente.AGUARDANDO.name()); // ponto 5

        if (entity.getEndereco() != null) {
            entity.getEndereco().setCliente(entity);
        }

        ClienteEntity salvo = clienteRepository.save(entity);
        return toDTO(salvo);
    }

  
    public ClienteDTO alterarCliente(ClienteDTO dto) {
        ClienteEntity entity = clienteRepository.findByCpf(dto.getCpf())
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + dto.getCpf() + " não encontrado."
            ));

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setSalario(dto.getSalario());

        // ponto 2: cria endereço se não existia, atualiza se já existe
        if (dto.getEndereco() != null) {
            if (entity.getEndereco() == null) {
                EnderecoEntity novoEndereco = new EnderecoEntity();
                novoEndereco.setCliente(entity);
                preencherEndereco(novoEndereco, dto.getEndereco());
                entity.setEndereco(novoEndereco);
            } else {
                preencherEndereco(entity.getEndereco(), dto.getEndereco());
            }
        }

        ClienteEntity atualizado = clienteRepository.save(entity);
        return toDTO(atualizado);
    }

    
    public void aprovarCliente(String cpf) {
        ClienteEntity entity = clienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + cpf + " não encontrado."
            ));

        entity.setStatus(StatusCliente.APROVADO.name());
        entity.setDataAprovacaoRejeicao(LocalDateTime.now()); 
        clienteRepository.save(entity);
    }

   
    public void rejeitarCliente(String cpf, String motivo) {
        ClienteEntity entity = clienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + cpf + " não encontrado."
            ));

        entity.setStatus(StatusCliente.REJEITADO.name());
        entity.setMotivoRejeicao(motivo);
        entity.setDataAprovacaoRejeicao(LocalDateTime.now()); 
        clienteRepository.save(entity);
    }

   
    public List<ClienteDTO> listarParaAprovar() {
        return clienteRepository.findByStatus(StatusCliente.AGUARDANDO.name()) // ponto 5
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

   
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findByStatus(StatusCliente.APROVADO.name()) // ponto 5
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

  
    public List<ClienteDTO> listarRelatorioAdm() {
        return clienteRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }


    public List<ClienteDTO> listarMelhoresClientes() {
        return clienteRepository.findByStatus(StatusCliente.APROVADO.name()) // ponto 5
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public ClienteDTO consultarClientePorCpf(String cpf) {
        ClienteEntity entity = clienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + cpf + " não encontrado."
            ));
        return toDTO(entity);
    }

    public ClienteDTO consultarClientePorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com ID " + id + " não encontrado."
            ));
        return toDTO(entity);
    }

    
    public ClienteDTO consultarClientePorIdUsuario(Integer idUsuario) {
        ClienteEntity entity = clienteRepository.findByIdUsuario(idUsuario)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com idUsuario " + idUsuario + " não encontrado."
            ));
        return toDTO(entity);
    }

   

    private ClienteDTO toDTO(ClienteEntity entity) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setIdGerente(entity.getIdGerente());
        dto.setCpf(entity.getCpf());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setSalario(entity.getSalario());
        dto.setMotivoRejeicao(entity.getMotivoRejeicao());
        dto.setDataAprovacaoRejeicao(entity.getDataAprovacaoRejeicao());

    
        if (entity.getStatus() != null) {
            try {
                dto.setStatus(StatusCliente.valueOf(entity.getStatus()));
            } catch (IllegalArgumentException e) {
                dto.setStatus(null);
            }
        }

        if (entity.getEndereco() != null) {
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setLogradouro(entity.getEndereco().getLogradouro());
            enderecoDTO.setNumero(entity.getEndereco().getNumero());
            enderecoDTO.setComplemento(entity.getEndereco().getComplemento());
            enderecoDTO.setCep(entity.getEndereco().getCep());
            enderecoDTO.setCidade(entity.getEndereco().getCidade());
            enderecoDTO.setEstado(entity.getEndereco().getEstado());
            dto.setEndereco(enderecoDTO);
        }

        return dto;
    }

    private ClienteEntity toEntity(ClienteDTO dto) {
        ClienteEntity entity = new ClienteEntity();
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setIdGerente(dto.getIdGerente());
        entity.setCpf(dto.getCpf());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setSalario(dto.getSalario());
        entity.setMotivoRejeicao(dto.getMotivoRejeicao());
        entity.setDataAprovacaoRejeicao(dto.getDataAprovacaoRejeicao());

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus().name());
        }

        if (dto.getEndereco() != null) {
            EnderecoEntity enderecoEntity = new EnderecoEntity();
            preencherEndereco(enderecoEntity, dto.getEndereco());
            entity.setEndereco(enderecoEntity);
        }

        return entity;
    }

    private void preencherEndereco(EnderecoEntity entity, EnderecoDTO dto) {
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setCep(dto.getCep());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
    }
}