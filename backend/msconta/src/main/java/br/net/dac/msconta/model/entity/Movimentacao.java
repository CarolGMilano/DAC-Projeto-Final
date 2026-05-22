package br.net.dac.msconta.model.entity;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    private String tipo; // DEPOSITO, SAQUE, TRANSFERENCIA

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "origem", referencedColumnName = "numero", nullable = true)
    private Conta origem;

    @ManyToOne
    @JoinColumn(name = "destino", referencedColumnName = "numero")
    private Conta destino; // nullable pois saque/deposito tem apenas uma conta

    public Movimentacao() {}

    public Movimentacao(String tipo, Double valor, Date data, Conta origem, Conta destino) {
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

    public Conta getOrigem() { return origem; }
    public void setOrigem(Conta origem) { this.origem = origem; }

    public Conta getDestino() { return destino; }
    public void setDestino(Conta destino) { this.destino = destino; }
}