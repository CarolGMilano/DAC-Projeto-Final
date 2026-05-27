package com.br.net.dac.mscontaquery.model.event;

public class ContaDeletedEvent {
    private boolean ativo;
    private String numeroConta;
    private Long idCliente;
    private Long idGerente;
    

    public ContaDeletedEvent() {}

    public ContaDeletedEvent(boolean ativo, String numeroConta, Long idCliente, Long idGerente) {
        this.ativo = ativo;
        this.numeroConta = numeroConta;
        this.idCliente = idCliente;
        this.idGerente = idGerente;
    }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdGerente() { return idGerente; }
    public void setIdGerente(Long idGerente) { this.idGerente = idGerente; }    
}
