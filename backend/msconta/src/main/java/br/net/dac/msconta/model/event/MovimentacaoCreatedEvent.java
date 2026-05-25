package br.net.dac.msconta.model.event;

import java.sql.Date;

public class MovimentacaoCreatedEvent {
    private Long id;
    private String tipo;
    private Double valor;
    private Date data;
    private String origem;
    private String destino;
    
    public MovimentacaoCreatedEvent() { }

    public MovimentacaoCreatedEvent(Long id, String tipo, Double valor, Date data, String origem, String destino) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.origem = origem;
        this.destino = destino;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }   
    
}
