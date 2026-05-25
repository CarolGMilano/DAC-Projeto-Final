package com.br.net.dac.mscontaquery.model.entity;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

@Id
private Long id;

@Column(name = "data")
private Date data;

@Column(name = "tipo")
private String tipo;

@Column(name = "origem")
private String origem;

@Column(name = "destino")
private String destino;

@Column(name = "valor")
private Double valor;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getOrigem() {
    return origem;
}

public void setOrigem(String origem) {
    this.origem = origem;
}

public String getDestino() {
    return destino;
}

public void setDestino(String destino) {
    this.destino = destino;
}

public Date getData() {
    return data;
}

public void setData(Date data) {
    this.data = data;
}

public String getTipo() {
    return tipo;
}

public void setTipo(String tipo) {
    this.tipo = tipo;
}

public Double getValor() {
    return valor;
}

public void setValor(Double valor) {
    this.valor = valor;
}



}
