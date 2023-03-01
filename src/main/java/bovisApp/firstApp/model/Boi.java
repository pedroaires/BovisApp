package bovisApp.firstApp.model;

import javax.persistence.*;

@Entity
@Table
public class Boi {
    @Id
    @GeneratedValue
    private Long id;
    private int numero;
    private double peso;
    @OneToOne
    private Raca raca;

    @ManyToOne
    private Lote lote;
    public Boi(){ }

    public Boi(Long id, int numero, double peso, Raca raca) {
        this.id = id;
        this.numero = numero;
        this.peso = peso;
        this.raca = raca;
    }

    public Boi(int numero, double peso, Raca raca) {
        this.numero = numero;
        this.peso = peso;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                '}';
    }
}
