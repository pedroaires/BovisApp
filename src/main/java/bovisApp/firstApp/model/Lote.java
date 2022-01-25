package bovisApp.firstApp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Lote {
    @Id
    @GeneratedValue
    private Long id;
    private Date dataCompra;
    private Date dataVenda;
    @OneToMany
    private List<Boi> bois;
    private double precoTotal;
    private double pesoInicialTotal;

    public Lote() {
    }

    public Lote(Date dataCompra, Date dataVenda, List<Boi> bois, double precoTotal, double pesoInicialTotal) {
        this.dataCompra = dataCompra;
        this.dataVenda = dataVenda;
        this.bois = bois;
        this.precoTotal = precoTotal;
        this.pesoInicialTotal = pesoInicialTotal;
    }

    public Lote(Date dataCompra, List<Boi> bois, double precoTotal, double pesoInicialTotal) {
        this.dataCompra = dataCompra;
        this.bois = bois;
        this.precoTotal = precoTotal;
        this.pesoInicialTotal = pesoInicialTotal;
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

    public List<Boi> getBois() {
        return bois;
    }

    public void setBois(List<Boi> bois) {
        this.bois = bois;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getPesoInicialTotal() {
        return pesoInicialTotal;
    }

    public void setPesoInicialTotal(double pesoInicialTotal) {
        this.pesoInicialTotal = pesoInicialTotal;
    }
}
