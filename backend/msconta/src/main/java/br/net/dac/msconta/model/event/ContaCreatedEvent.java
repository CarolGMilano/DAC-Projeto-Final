package br.net.dac.msconta.model.event;

public class ContaCreatedEvent {
    private String numeroConta;
    private double saldo;
    private double limite;
    
    public ContaCreatedEvent() {
    }

    public ContaCreatedEvent(String numeroConta, double saldo, double limite) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.limite = limite;
    }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }    
}
