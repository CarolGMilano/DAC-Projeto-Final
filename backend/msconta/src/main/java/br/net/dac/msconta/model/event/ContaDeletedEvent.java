package br.net.dac.msconta.model.event;

public class ContaDeletedEvent {
    private boolean ativo;
    private String numero;
    private String clienteCpf;
    private String gerenteCpf;
    

    public ContaDeletedEvent() {}

    public ContaDeletedEvent(boolean ativo, String numero, String clienteCpf, String gerenteCpf) {
        this.ativo = ativo;
        this.numero = numero;
        this.clienteCpf = clienteCpf;
        this.gerenteCpf = gerenteCpf;
    }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero;}

    public String getClienteCpf() { return clienteCpf; }
    public void setClienteCpf(String clienteCpf) { this.clienteCpf = clienteCpf; }

    public String getGerenteCpf() { return gerenteCpf; }
    public void setGerenteCpf(String gerenteCpf) { this.gerenteCpf = gerenteCpf; }

    
}
