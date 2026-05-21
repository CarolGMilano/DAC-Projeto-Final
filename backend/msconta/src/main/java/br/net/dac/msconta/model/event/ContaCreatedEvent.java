package br.net.dac.msconta.model.event;

public class ContaCreatedEvent {
    private String numero;
    private double saldo;
    private double limite;
    
    public ContaCreatedEvent() {
    }

    public ContaCreatedEvent(String numero, double saldo, double limite) {
        this.numero = numero;
        this.saldo = saldo;
        this.limite = limite;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }    
}
