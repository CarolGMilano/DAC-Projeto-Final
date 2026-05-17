package com.br.net.dac.mscontaquery.model.dto.response;

public class DashboardAdminDTO {

private String nomeGerente;
private Double totalSaldoNegativo;
private Double totalSaldoPositivo;
private Integer totalNumeroDeClientes;

public DashboardAdminDTO() {
}

public String getNomeGerente() {
    return nomeGerente;
}

public void setNomeGerente(String nomeGerente) {
    this.nomeGerente = nomeGerente;
}

public Integer getTotalNumeroDeClientes() {
    return totalNumeroDeClientes;
}

public void setTotalNumeroDeClientes(Integer totalNumeroDeClientes) {
    this.totalNumeroDeClientes = totalNumeroDeClientes;
}

public Double getTotalSaldoPositivo() {
    return totalSaldoPositivo;
}

public void setTotalSaldoPositivo(Double totalSaldoPositivo) {
    this.totalSaldoPositivo = totalSaldoPositivo;
}

public Double getTotalSaldoNegativo() {
    return totalSaldoNegativo;
}

public void setTotalSaldoNegativo(Double totalSaldoNegativo) {
    this.totalSaldoNegativo = totalSaldoNegativo;
}



}
