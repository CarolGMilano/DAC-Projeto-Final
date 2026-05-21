package br.net.dac.msconta.model.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @Column(name = "numero")
    private String numero;

    @Column(name = "gerente_cpf")
    private String gerenteCpf;

    @Column(name = "cliente_cpf")
    private String clienteCpf;

    @Column(name = "data_criacao")
    private Date data;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "limite")
    private double limite;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    public Conta() {}

    public Conta(String numero, String gerenteCpf, String clienteCpf, Date data, double saldo, double limite,
            Boolean ativo) {
        this.numero = numero;
        this.gerenteCpf = gerenteCpf;
        this.clienteCpf = clienteCpf;
        this.data = data;
        this.saldo = saldo;
        this.limite = limite;
        this.ativo = ativo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public Boolean getAtivo() {return ativo;}
    public void setAtivo(Boolean ativo) {this.ativo = ativo;}

    public List<Movimentacao> getMovimentacoes() { return movimentacoes; }
    public void setMovimentacoes(List<Movimentacao> movimentacoes) { this.movimentacoes = movimentacoes; }

}