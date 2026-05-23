package br.net.dac.msconta.model.event;

import java.sql.Date;

public class ContaCreatedEvent {
    private String numero;
    private String gerenteCpf;
    private String clienteCpf;
    private Date data;
    private Double saldo;
    private Double limite;
    private Boolean ativo;
            
    public ContaCreatedEvent() { }

    public ContaCreatedEvent(String numero, String gerenteCpf, String clienteCpf, Date data, Double saldo,
            Double limite, Boolean ativo) {
        this.numero = numero;
        this.gerenteCpf = gerenteCpf;
        this.clienteCpf = clienteCpf;
        this.data = data;
        this.saldo = saldo;
        this.limite = limite;
        this.ativo = ativo;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getGerenteCpf() { return gerenteCpf; }
    public void setGerenteCpf(String gerenteCpf) { this.gerenteCpf = gerenteCpf; }

    public String getClienteCpf() { return clienteCpf; }
    public void setClienteCpf(String clienteCpf) { this.clienteCpf = clienteCpf; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public Double getLimite() { return limite; }
    public void setLimite(Double limite) { this.limite = limite; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }  
        
}
