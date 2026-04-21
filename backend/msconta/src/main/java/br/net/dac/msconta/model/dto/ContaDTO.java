package br.net.dac.msconta.model.dto;

import java.sql.Date;

public class ContaDTO {
    private Long id;
    private Long idGerente;
    private Long idCliente;
    private String numeroConta;
    private Date dataCriacao;
    private double saldo;
    private double limite;

    public ContaDTO() {
    }

    public ContaDTO(long id, String numeroConta, Date dataCriacao, double saldo, double limite) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.limite = limite;
    }

    // Getters e Setters

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getIdGerente() {return idGerente;}
    public void setIdGerente(Long idGerente) {this.idGerente = idGerente;}

    public Long getIdCliente() {return idCliente;}
    public void setIdCliente(Long idCliente) {this.idCliente = idCliente;}

    public String getNumeroConta() {return numeroConta;}
    public void setNumeroConta(String numeroConta) {this.numeroConta = numeroConta;}

    public Date getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(Date dataCriacao) {this.dataCriacao = dataCriacao;}

    public Double getSaldo() {return saldo;}
    public void setSaldo(Double saldo) {this.saldo = saldo;}

    public Double getLimite() {return limite;}
    public void setLimite(Double limite) {this.limite = limite;}
}