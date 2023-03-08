package bovisApp.firstApp.model;

import bovisApp.firstApp.model.enumeration.EstadoBoi;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table
public class Boi {
    @Id
    @GeneratedValue
    private Long id;
    private int numero;

    @ManyToMany
    private Map<Date, Pesagem> pesagens;

    @OneToOne
    private Raca raca;

    @ManyToOne
    private Lote lote;

    private EstadoBoi estadoBoi;
    public Boi(){ }

    public Boi(Long id, int numero, Raca raca) {
        this.id = id;
        this.numero = numero;
        this.raca = raca;
    }

    public Boi(int numero, Raca raca) {
        this.numero = numero;
        this.raca = raca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "Boi{" +
                "id=" + id +
                ", numero=" + numero +
                ", raca='" + raca + '\'' +
                '}';
    }
}
