package br.net.dac.mscliente.model.dto;

import br.net.dac.mscliente.model.entity.StatusCliente;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClienteDTO {
    private Long id;
    private Integer idUsuario;
    private Integer idGerente; 
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private BigDecimal salario;
    private EnderecoDTO endereco;
    private StatusCliente status; 
    private String motivoRejeicao;
    private LocalDateTime dataAprovacaoRejeicao;

    public ClienteDTO() {}

   

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdGerente() { return idGerente; } // Corrigido para Integer
    public void setIdGerente(Integer idGerente) { this.idGerente = idGerente; } // Corrigido para Integer

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public EnderecoDTO getEndereco() { return endereco; }
    public void setEndereco(EnderecoDTO endereco) { this.endereco = endereco; }

    public StatusCliente getStatus() { return status; } // Retorna o Enum corretamente
    public void setStatus(StatusCliente status) { this.status = status; }

    public String getMotivoRejeicao() { return motivoRejeicao; }
    public void setMotivoRejeicao(String motivoRejeicao) { this.motivoRejeicao = motivoRejeicao; }

    public LocalDateTime getDataAprovacaoRejeicao() { return dataAprovacaoRejeicao; }
    public void setDataAprovacaoRejeicao(LocalDateTime dataAprovacaoRejeicao) { 
        this.dataAprovacaoRejeicao = dataAprovacaoRejeicao; 
    }
}