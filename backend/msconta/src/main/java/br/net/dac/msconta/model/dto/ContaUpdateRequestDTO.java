package br.net.dac.msconta.model.dto;

public class ContaUpdateRequestDTO {
    private String clienteCpf;
    private String gerenteCpf;
    private Double salario;
    private Boolean salarioAlterado;

    public ContaUpdateRequestDTO() {
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public String getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Boolean getSalarioAlterado() {
        return salarioAlterado;
    }

    public void setSalarioAlterado(Boolean salarioAlterado) {
        this.salarioAlterado = salarioAlterado;
    }

}
