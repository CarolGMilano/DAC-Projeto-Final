package br.net.bantads.generico.model;

public class ClassExemplo {
    private int id;
    private String nome;
    private String senha;

    public ClassExemplo() {
        super();
    }

    public ClassExemplo(int id, String nome, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(int id) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(int id) {
        this.senha = senha;
    }

}
