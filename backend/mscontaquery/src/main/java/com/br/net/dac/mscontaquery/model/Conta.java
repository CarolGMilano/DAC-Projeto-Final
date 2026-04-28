package com.br.net.dac.mscontaquery.model;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name= "ativo")
    private boolean ativo;
    
    @Column(name = "idGerente")
    private Long idGerente;

    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "numeroConta")
    private String numeroConta;

    @Column(name = "dataCriacao")
    private Date dataCriacao;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "limite")
    private double limite;

    public Conta() {
    }

    public Conta(boolean ativo, Long id, Long idGerente, Long idCliente, String numeroConta, Date dataCriacao, double saldo, double limite) {
        this.ativo = ativo;
        this.id = id;
        this.idGerente = idGerente;
        this.idCliente = idCliente;
        this.numeroConta = numeroConta;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.limite = limite;
    }

    // Getters e Setters

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}