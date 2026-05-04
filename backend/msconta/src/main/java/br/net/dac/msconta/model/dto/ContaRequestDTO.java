package br.net.dac.msconta.model.dto;

public class ContaRequestDTO {
    private boolean ativo;
    private Long idGerente;
    private Long idCliente;
    private double saldo;
    private double limite;

    public ContaRequestDTO() {
    }

    public ContaRequestDTO(boolean ativo, Long idGerente, Long idCliente, double saldo, double limite) {
        this.ativo = ativo;
        this.idGerente = idGerente;
        this.idCliente = idCliente;
        this.saldo = saldo;
        this.limite = limite;
    }

    // Getters e Setters

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

}
