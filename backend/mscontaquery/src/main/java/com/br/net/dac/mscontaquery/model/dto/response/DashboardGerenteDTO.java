package com.br.net.dac.mscontaquery.model.dto.response;

public class DashboardGerenteDTO {

private Long idGerente;
private String nomeGerente;
private Integer totalClientes;
private Double totalSaldoPositivo;
private Double totalSaldoNegativo;
private Double totalSaldoGeral;

public DashboardGerenteDTO() {
}

public DashboardGerenteDTO(Long idGerente, String nomeGerente, Integer totalClientes, Double totalSaldoPositivo,
        Double totalSaldoNegativo, Double totalSaldoGeral) {
    this.idGerente = idGerente;
    this.nomeGerente = nomeGerente;
    this.totalClientes = totalClientes;
    this.totalSaldoPositivo = totalSaldoPositivo;
    this.totalSaldoNegativo = totalSaldoNegativo;
    this.totalSaldoGeral = totalSaldoGeral;
}
public Long getIdGerente() {
    return idGerente;
}
public String getNomeGerente() {
    return nomeGerente;
}
public Integer getTotalClientes() {
    return totalClientes;
}
public Double getTotalSaldoPositivo() {
    return totalSaldoPositivo;
}
public Double getTotalSaldoNegativo() {
    return totalSaldoNegativo;
}
public Double getTotalSaldoGeral() {
    return totalSaldoGeral;
}
public void setIdGerente(Long idGerente) {
    this.idGerente = idGerente;
}
public void setNomeGerente(String nomeGerente) {
    this.nomeGerente = nomeGerente;
}
public void setTotalClientes(Integer totalClientes) {
    this.totalClientes = totalClientes;
}
public void setTotalSaldoPositivo(Double totalSaldoPositivo) {
    this.totalSaldoPositivo = totalSaldoPositivo;
}
public void setTotalSaldoNegativo(Double totalSaldoNegativo) {
    this.totalSaldoNegativo = totalSaldoNegativo;
}
public void setTotalSaldoGeral(Double totalSaldoGeral) {
    this.totalSaldoGeral = totalSaldoGeral;
}

}
