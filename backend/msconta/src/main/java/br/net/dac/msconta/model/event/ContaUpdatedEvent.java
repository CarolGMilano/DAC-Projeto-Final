package br.net.dac.msconta.model.event;

public class ContaUpdatedEvent {
    private String numero;
    private boolean ativo; 
    
    public ContaUpdatedEvent() {
    }

    public ContaUpdatedEvent(String numero, boolean ativo) {
        this.numero = numero;
        this.ativo = ativo;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
