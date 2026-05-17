package com.br.net.dac.mscontaquery.model.dto.response;

public class ConsultaDeClientesDTO {
private String cpfCliente;
private String nomeCliente;
private String cidade;
private String estado;
private Double saldo;
private Double limite;

public ConsultaDeClientesDTO() {
}

public String getCpfCliente() {
    return cpfCliente;
}

public void setCpfCliente(String cpfCliente) {
    this.cpfCliente = cpfCliente;
}

public String getNomeCliente() {
    return nomeCliente;
}

public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}

public String getCidade() {
    return cidade;
}

public void setCidade(String cidade) {
    this.cidade = cidade;
}

public String getEstado() {
    return estado;
}

public void setEstado(String estado) {
    this.estado = estado;
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

}
