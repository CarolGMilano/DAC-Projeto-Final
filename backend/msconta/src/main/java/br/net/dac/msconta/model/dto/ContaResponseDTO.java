package br.net.dac.msconta.model.dto;

import java.sql.Date;

public class ContaResponseDTO {
    private Boolean ativo;
    private String gerenteCpf;
    private String clienteCpf;
    private String numero;
    private Date data;
    private Double saldo;
    private Double limite;
    

    
    public ContaResponseDTO(Boolean ativo, String gerenteCpf, String clienteCpf, String numero, Date data, Double saldo,
            Double limite) {
        this.ativo = ativo;
        this.gerenteCpf = gerenteCpf;
        this.clienteCpf = clienteCpf;
        this.numero = numero;
        this.data = data;
        this.saldo = saldo;
        this.limite = limite;
        this.data = data;
    }
    public Boolean isAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) {this.ativo = ativo; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
    
    public Double getLimite() { return limite; }
    public void setLimite(Double limite) { this.limite = limite; }

    public String getGerenteCpf() {return gerenteCpf;}
    public void setGerenteCpf(String gerenteCpf) {this.gerenteCpf = gerenteCpf;}

    public String getClienteCpf() {return clienteCpf;}
    public void setClienteCpf(String clienteCpf) {this.clienteCpf = clienteCpf;}    
    
}


