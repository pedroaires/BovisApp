package bovisApp.firstApp.model;

import bovisApp.firstApp.model.enumeration.EstadoLote;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Lote {
    @Id
    @GeneratedValue
    private Long id;
    private Date dataCompra;
    private Date dataVenda;

    private EstadoLote estadoLote;

    public Lote() {
    }

    public Lote(Date dataCompra, EstadoLote estadoLote) {
        this.dataCompra = dataCompra;
        this.estadoLote = estadoLote;
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

    public EstadoLote getEstado() {
        return estadoLote;
    }

    public void setEstado(EstadoLote estadoLote) {
        this.estadoLote = estadoLote;
    }
}
