package bovisApp.firstApp.model;

import bovisApp.firstApp.model.enumeration.Estado;

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

    private Estado estado;

    public Lote() {
    }

    public Lote(Date dataCompra, Estado estado) {
        this.dataCompra = dataCompra;
        this.estado = estado;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
