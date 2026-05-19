package br.net.dac.msconta.model.event;

import java.sql.Date;

public class ContaUpdatedEvent {
    private String numeroConta;
    private boolean ativo;
    private Long idGerente;
    private Long idCliente;
    private Date dataCriacao;
    private double saldo;
    private double limite;
    
    
    public ContaUpdatedEvent() {
    }

    public ContaUpdatedEvent(String numeroConta, boolean ativo, Long idGerente, Long idCliente, Date dataCriacao,
            double saldo, double limite) {
        this.numeroConta = numeroConta;
        this.ativo = ativo;
        this.idGerente = idGerente;
        this.idCliente = idCliente;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.limite = limite;
    }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Long getIdGerente() { return idGerente; }
    public void setIdGerente(Long idGerente) { this.idGerente = idGerente; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public double getLimite() { return limite;}
    public void setLimite(double limite) { this.limite = limite; }  
}
