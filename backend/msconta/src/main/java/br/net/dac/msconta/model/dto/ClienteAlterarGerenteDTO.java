package br.net.dac.msconta.model.dto;

public class ClienteAlterarGerenteDTO {
  private String gerenteAntigo;
  private String gerenteNovo;
  public ClienteAlterarGerenteDTO() {
  }
  public ClienteAlterarGerenteDTO(String gerenteAntigo, String gerenteNovo) {
    this.gerenteAntigo = gerenteAntigo;
    this.gerenteNovo = gerenteNovo;
  }
  public ClienteAlterarGerenteDTO(String gerenteNovo) {
    this.gerenteNovo = gerenteNovo;
  }
  public String getGerenteAntigo() {
    return gerenteAntigo;
  }
  public void setGerenteAntigo(String gerenteAntigo) {
    this.gerenteAntigo = gerenteAntigo;
  }
  public String getGerenteNovo() {
    return gerenteNovo;
  }
  public void setGerenteNovo(String gerenteNovo) {
    this.gerenteNovo = gerenteNovo;
  }
}
