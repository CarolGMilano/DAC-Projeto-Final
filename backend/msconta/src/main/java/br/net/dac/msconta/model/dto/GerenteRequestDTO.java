package br.net.dac.msconta.model.dto;

public class GerenteRequestDTO {
    String gerenteCpf;

    public GerenteRequestDTO(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public String getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }
}