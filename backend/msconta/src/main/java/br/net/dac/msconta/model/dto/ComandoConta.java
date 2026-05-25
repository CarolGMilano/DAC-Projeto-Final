package br.net.dac.msconta.model.dto;

public class ComandoConta {
    private String tipo;
    private ContaResponseDTO payload;

    public ComandoConta(){}

    public ComandoConta(String tipo, ContaResponseDTO payload) {
        this.tipo = tipo;
        this.payload = payload;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public ContaResponseDTO getPayload() { return payload; }
    public void setPayload(ContaResponseDTO payload) { this.payload = payload; }   
}
