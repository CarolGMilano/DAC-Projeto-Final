package com.br.net.dac.mscontaquery.model.dto.response;

public class DadoGerente {
    private String cpf;
    private String nome;
    private String email;
    private String tipo;

    public DadoGerente() {}
    
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    
}
