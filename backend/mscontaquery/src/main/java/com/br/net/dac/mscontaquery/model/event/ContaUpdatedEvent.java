package com.br.net.dac.mscontaquery.model.event;

import java.sql.Date;

public class ContaUpdatedEvent {
    private String numeroConta;
    private boolean ativo; 
    
    public ContaUpdatedEvent() {
    }

    public ContaUpdatedEvent(String numeroConta, boolean ativo) {
        this.numeroConta = numeroConta;
        this.ativo = ativo;
    }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
