package br.net.dac.msconta.model.event;

public class ContaUpdatedEvent {
    private String numero; // Para encontrar a conta que foi atualizada
    private String gerenteCpf;
    private Double saldo;
    private Double limite;
            
    public ContaUpdatedEvent() { }

    public ContaUpdatedEvent(String numero, String gerenteCpf, Double saldo, Double limite) {
        this.numero = numero;
        this.gerenteCpf = gerenteCpf;
        this.saldo = saldo;
        this.limite = limite;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getGerenteCpf() { return gerenteCpf; }
    public void setGerenteCpf(String gerenteCpf) { this.gerenteCpf = gerenteCpf; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public Double getLimite() { return limite; }
    public void setLimite(Double limite) { this.limite = limite; }
}