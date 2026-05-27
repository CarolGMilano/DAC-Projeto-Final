package com.br.net.dac.mscontaquery.model.dto;

public class ContaResumoDTO {
private Double saldo;
private Double limite;
private String conta;

public ContaResumoDTO() {
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
public String getConta() {
    return conta;
}
public void setConta(String conta) {
    this.conta = conta;
}

}
