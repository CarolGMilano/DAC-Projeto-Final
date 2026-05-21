package br.net.dac.msconta.model.dto;

public class ComandoConta {
    private String tipo;
    private ContaDTO payload;

    public ComandoConta(){}

    public ComandoConta(String tipo, ContaDTO payload) {
        this.tipo = tipo;
        this.payload = payload;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public ContaDTO getPayload() { return payload; }
    public void setPayload(ContaDTO payload) { this.payload = payload; }   
}
