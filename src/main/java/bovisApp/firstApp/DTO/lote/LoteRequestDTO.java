package bovisApp.firstApp.DTO.lote;

import bovisApp.firstApp.model.enumeration.EstadoLote;

import java.util.Date;
import java.util.Objects;

public class LoteRequestDTO {
    private Date dataCompra;
    private Date dataVenda;
    private String descricao;
    private EstadoLote estadoLote;

    public LoteRequestDTO(Date dataCompra, Date dataVenda, String descricao, EstadoLote estadoLote) {
        this.dataCompra = dataCompra;
        this.dataVenda = dataVenda;
        this.descricao = descricao;
        this.estadoLote = estadoLote;
    }

    public LoteRequestDTO(){

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

    public EstadoLote getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(EstadoLote estadoLote) {
        this.estadoLote = estadoLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoteRequestDTO that = (LoteRequestDTO) o;
        return dataCompra.equals(that.dataCompra) && Objects.equals(dataVenda, that.dataVenda) && Objects.equals(descricao, that.descricao) && estadoLote == that.estadoLote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataCompra, dataVenda, descricao, estadoLote);
    }
}
