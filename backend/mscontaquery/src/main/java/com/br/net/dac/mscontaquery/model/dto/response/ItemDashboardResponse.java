package com.br.net.dac.mscontaquery.model.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ItemDashboardResponse {

private DadoGerente gerente;
private List<DadoConta> clientes = new ArrayList<DadoConta>();
private Double saldoNegativo;
private Double saldoPositivo;

public ItemDashboardResponse() {}

public DadoGerente getGerente() {
    return gerente;
}

public void setGerente(DadoGerente gerente) {
    this.gerente = gerente;
}

public Double getSaldoNegativo() {
    return saldoNegativo;
}

public void setSaldoNegativo(Double saldoNegativo) {
    this.saldoNegativo = saldoNegativo;
}

public Double getSaldoPositivo() {
    return saldoPositivo;
}

public void setSaldoPositivo(Double saldoPositivo) {
    this.saldoPositivo = saldoPositivo;
}

public List<DadoConta> getClientes() {
    return clientes;
}

public void setClientes(List<DadoConta> clientes) {
    this.clientes = clientes;
}




}
