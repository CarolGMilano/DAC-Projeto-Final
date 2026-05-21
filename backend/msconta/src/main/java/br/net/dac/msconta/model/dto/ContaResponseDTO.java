package br.net.dac.msconta.model.dto;

import java.util.Date;

public class ContaResponseDTO {
    private boolean ativo;
    private String gerenteCpf;
    private String clienteCpf;
    private String numero;
    private Date data;
    private double saldo;
    private double limite;
    

    
    public ContaResponseDTO(boolean ativo, String gerenteCpf, String clienteCpf, String numero, Date data, double saldo,
            double limite) {
        this.ativo = ativo;
        this.gerenteCpf = gerenteCpf;
        this.clienteCpf = clienteCpf;
        this.numero = numero;
        this.data = data;
        this.saldo = saldo;
        this.limite = limite;
    }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) {this.ativo = ativo; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    public String getGerenteCpf() {return gerenteCpf;}
    public void setGerenteCpf(String gerenteCpf) {this.gerenteCpf = gerenteCpf;}

    public String getClienteCpf() {return clienteCpf;}
    public void setClienteCpf(String clienteCpf) {this.clienteCpf = clienteCpf;}    
    
}


