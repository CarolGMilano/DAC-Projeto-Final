package br.net.dac.msconta.model.dto;

import java.sql.Date;

public class ContaDTO {
    private long id;
    private long idGerente;
    private long idCliente;
    private String numeroConta;
    private Date dataCriacao;
    private double saldo;
    private double limite;

    public ContaDTO() {
    }

    public ContaDTO(int id, String numeroConta, Date dataCriacao, double saldo, double limite) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.limite = limite;
    }

    // Getters e Setters

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public long getIdGerente() {return idGerente;}
    public void setIdGerente(long idGerente) {this.idGerente = idGerente;}

    public long getIdCliente() {return idCliente;}
    public void setIdCliente(long idCliente) {this.idCliente = idCliente;}

    public String getNumeroConta() {return numeroConta;}
    public void setNumeroConta(String numeroConta) {this.numeroConta = numeroConta;}

    public Date getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(Date dataCriacao) {this.dataCriacao = dataCriacao;}

    public double getSaldo() {return saldo;}
    public void setSaldo(double saldo) {this.saldo = saldo;}

    public double getLimite() {return limite;}
    public void setLimite(double limite) {this.limite = limite;}
}