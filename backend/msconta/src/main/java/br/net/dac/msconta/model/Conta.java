package br.net.dac.msconta.model;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "conta")
public class Conta {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Isso é pra dizer que o id vai ser gerado pelo banco automaticamente
    private long id;

    @Column(name = "idGerente")
    private long idGerente;

    @Column(name = "idCliente")
    private long idCliente;

    @Column(name = "numeroConta")
    private String numeroConta;

    @Column(name = "dataCriacao")
    private Date dataCriacao;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "limite")
    private double limite;

    public Conta() {
    }

    public Conta(int id, String numeroConta, Date dataCriacao, double saldo, double limite) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.limite = limite;
    }

    // Getters e Setters

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public long getIdGerente() {return idGerente;}
    public void setIdGerente(long idGerente) {this.idGerente = idGerente;}

    public long getIdCliente() {return idCliente;}
    public void setIdCliente(long idCliente) {this.idCliente = idCliente;}

    public String getNumeroConta() {return numeroConta;}
    public void setNumeroConta(String numeroConta) {this.numeroConta = numeroConta;}

    public Date getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(Date dataCriacao) {this.dataCriacao = dataCriacao;}

    public double getSaldo() {return saldo;}
    public void setSaldo(double saldo) {this.saldo = saldo;}

    public double getLimite() {return limite;}
    public void setLimite(double limite) {this.limite = limite;}
}