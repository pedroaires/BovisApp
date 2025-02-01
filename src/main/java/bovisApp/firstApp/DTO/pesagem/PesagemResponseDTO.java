package bovisApp.firstApp.DTO.pesagem;

import bovisApp.firstApp.model.Medicacao;
import bovisApp.firstApp.model.Pesagem;

import java.util.Date;
import java.util.List;

public class PesagemResponseDTO {
private Long id;
    private Date data;
    private Double peso;
    private String descricao;
    private List<Medicacao> medicacoes;

    public PesagemResponseDTO(Long id, Date data, Double peso, String descricao, List<Medicacao> medicacoes) {
        this.id = id;
        this.data = data;
        this.peso = peso;
        this.descricao = descricao;
        this.medicacoes = medicacoes;
    }

    public PesagemResponseDTO(Pesagem pesagem){
        this.id = pesagem.getId();
        this.data = pesagem.getData();
        this.peso = pesagem.getPeso();
        this.descricao = pesagem.getDescricao();
        this.medicacoes = pesagem.getMedicacoes();
    }


}
