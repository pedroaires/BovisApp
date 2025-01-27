package bovisApp.firstApp.DTO.lote;

import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.enumeration.EstadoLote;

import java.util.Date;
import java.util.Objects;

public class LoteResponseDTO {
    private Long id;
    private Date dataCompra;
    private Date dataVenda;
    private String descricao;
    private String estadoLote;


    public LoteResponseDTO(Lote lote){
        this.id = lote.getId();
        this.dataCompra = lote.getDataCompra();
        this.dataVenda = lote.getDataVenda();
        this.descricao = lote.getDescricao();
        this.estadoLote = lote.getEstado().toString();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(String estadoLote) {
        this.estadoLote = estadoLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoteResponseDTO that = (LoteResponseDTO) o;
        return id.equals(that.id) && Objects.equals(dataCompra, that.dataCompra) && Objects.equals(dataVenda, that.dataVenda) && Objects.equals(descricao, that.descricao) && Objects.equals(estadoLote, that.estadoLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCompra, dataVenda, descricao, estadoLote);
    }
}
