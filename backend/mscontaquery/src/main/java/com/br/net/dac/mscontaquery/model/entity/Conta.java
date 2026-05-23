package com.br.net.dac.mscontaquery.model.entity;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @Column(name = "numero")
    private String numero;

    @Column(name = "gerente_cpf")
    private String gerenteCpf;

    @Column(name = "cliente_cpf")
    private String clienteCpf;

    @Column(name = "data_criacao")
    private Date data;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "limite")
    private Double limite;

    @Column(name = "ativo")
    private Boolean ativo;
    
public String getNumero() {
    return numero;
}

public String getGerenteCpf() {
    return gerenteCpf;
}

public void setGerenteCpf(String gerenteCpf) {
    this.gerenteCpf = gerenteCpf;
}

public String getClienteCpf() {
    return clienteCpf;
}

public void setClienteCpf(String clienteCpf) {
    this.clienteCpf = clienteCpf;
}

public Date getData() {
    return data;
}

public void setData(Date data) {
    this.data = data;
}

public void setNumero(String numero) {
    this.numero = numero;
}

public Double getSaldo() {
    return saldo;
}

public void setSaldo(Double saldo) {
    this.saldo = saldo;
}

public Double getLimite() {
    return limite;
}

public void setLimite(Double limite) {
    this.limite = limite;
}

public Boolean getAtivo() {
    return ativo;
}

public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
}
}
