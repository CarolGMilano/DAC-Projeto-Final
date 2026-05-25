package br.net.dac.msconta.model.dto;

public class TransferenciaRequestDTO {
    private String destino;
    private Double valor;

    public TransferenciaRequestDTO(){}

    public TransferenciaRequestDTO(String destino, Double valor) {
        this.destino = destino;
        this.valor = valor;
    }
    
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
}
