package bovisApp.firstApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Pesagem {
    @Id
    @GeneratedValue
    private Long id;

    private Date data;

    @ManyToOne
    private Boi boi;

    private Double peso;

    private String descricao;

    @ManyToMany
    private List<Medicacao> medicacoes;


    public Pesagem(Date data, Double peso, String descricao, List<Medicacao> medicacoes) {
        this.data = data;
        this.peso = peso;
        this.descricao = descricao;
        this.medicacoes = medicacoes;
    }

    public Pesagem() {

    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public void setDescricao(String comentario) {
        this.descricao = comentario;
    }

    public List<Medicacao> getMedicacoes() {
        return medicacoes;
    }

    public void setMedicacoes(List<Medicacao> medicacoes) {
        this.medicacoes = medicacoes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
