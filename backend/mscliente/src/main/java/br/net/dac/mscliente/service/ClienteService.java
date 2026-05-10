package br.net.dac.mscliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.mscliente.model.dto.ClienteDTO;
import br.net.dac.mscliente.model.dto.EnderecoDTO;
import br.net.dac.mscliente.model.entity.ClienteEntity;
import br.net.dac.mscliente.model.entity.EnderecoEntity;
import br.net.dac.mscliente.repository.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // R1 - Autocadastro
    public ClienteDTO cadastrarCliente(ClienteDTO dto) {
        if (clienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("Cliente com CPF " + dto.getCpf() + " já cadastrado.");
        }

        ClienteEntity entity = toEntity(dto);

        if (entity.getEndereco() != null) {
            entity.getEndereco().setCliente(entity);
        }

        ClienteEntity salvo = clienteRepository.save(entity);
        return toDTO(salvo);
    }

    // R4 - Alterar perfil (menos CPF)
    public ClienteDTO alterarCliente(ClienteDTO dto) {
        ClienteEntity entity = clienteRepository.findByCpf(dto.getCpf())
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + dto.getCpf() + " não encontrado."
            ));

        entity.setNome(dto.getNome());
        entity.setSalario(dto.getSalario());

        if (dto.getEndereco() != null && entity.getEndereco() != null) {
            entity.getEndereco().setLogradouro(dto.getEndereco().getLogradouro());
            entity.getEndereco().setNumero(dto.getEndereco().getNumero());
            entity.getEndereco().setComplemento(dto.getEndereco().getComplemento());
            entity.getEndereco().setCep(dto.getEndereco().getCep());
            entity.getEndereco().setCidade(dto.getEndereco().getCidade());
            entity.getEndereco().setEstado(dto.getEndereco().getEstado());
        }

        ClienteEntity atualizado = clienteRepository.save(entity);
        return toDTO(atualizado);
    }

    // R12 - Listar todos os clientes
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // R13 - Consultar por CPF
    public ClienteDTO consultarClientePorCpf(String cpf) {
        ClienteEntity entity = clienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com CPF " + cpf + " não encontrado."
            ));
        return toDTO(entity);
    }

    // Consultar por ID interno
    public ClienteDTO consultarClientePorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com ID " + id + " não encontrado."
            ));
        return toDTO(entity);
    }

    // Consultar por idUsuario (usado pelo ms-auth)
    public ClienteDTO consultarClientePorIdUsuario(Long idUsuario) {
        ClienteEntity entity = clienteRepository.findByIdUsuario(idUsuario)
            .orElseThrow(() -> new RuntimeException(
                "Cliente com idUsuario " + idUsuario + " não encontrado."
            ));
        return toDTO(entity);
    }

    // -----------------------------------------------
    // Conversores Entity <-> DTO
    // -----------------------------------------------

    private ClienteDTO toDTO(ClienteEntity entity) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setCpf(entity.getCpf());
        dto.setNome(entity.getNome());
        dto.setSalario(entity.getSalario());

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
        entity.setCpf(dto.getCpf());
        entity.setNome(dto.getNome());
        entity.setSalario(dto.getSalario());

        if (dto.getEndereco() != null) {
            EnderecoEntity enderecoEntity = new EnderecoEntity();
            enderecoEntity.setLogradouro(dto.getEndereco().getLogradouro());
            enderecoEntity.setNumero(dto.getEndereco().getNumero());
            enderecoEntity.setComplemento(dto.getEndereco().getComplemento());
            enderecoEntity.setCep(dto.getEndereco().getCep());
            enderecoEntity.setCidade(dto.getEndereco().getCidade());
            enderecoEntity.setEstado(dto.getEndereco().getEstado());
            entity.setEndereco(enderecoEntity);
        }

        return entity;
    }
}