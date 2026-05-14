package com.br.net.dac.mscontaquery.model.dto.response;

public class DashboardGerenteDTO {
private String numeroConta;
private String cpfCliente;
private String email;
private Double salario;

public DashboardGerenteDTO() {
}

public String getCpfCliente() {
    return cpfCliente;
}

public void setCpfCliente(String cpfCliente) {
    this.cpfCliente = cpfCliente;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public Double getSalario() {
    return salario;
}

public void setSalario(Double salario) {
    this.salario = salario;
}

public String getNumeroConta() {
    return numeroConta;
}

public void setNumeroConta(String numeroConta) {
    this.numeroConta = numeroConta;
}


}
