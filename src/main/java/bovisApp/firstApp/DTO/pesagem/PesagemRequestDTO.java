package bovisApp.firstApp.DTO.pesagem;

import bovisApp.firstApp.model.Medicacao;

import java.util.Date;
import java.util.List;

public class PesagemRequestDTO {
    private Long boiId;
    private Double peso;
    private String descricao;

    private Date data;

    private List<String> medicacoes;

    public PesagemRequestDTO(Long boiId, Double peso, String descricao, Date data, List<String> medicacoes) {
        this.boiId = boiId;
        this.peso = peso;
        this.descricao = descricao;
        this.data = data;
        this.medicacoes = medicacoes;
    }

    public Long getBoiId() {
        return boiId;
    }

    public void setBoiId(Long boiId) {
        this.boiId = boiId;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<String> getMedicacoes() {
        return medicacoes;
    }

    public void setMedicacoes(List<String> medicacoes) {
        this.medicacoes = medicacoes;
    }
}
