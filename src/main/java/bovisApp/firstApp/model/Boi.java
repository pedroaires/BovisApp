package bovisApp.firstApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Boi {
    @Id
    @GeneratedValue
    private Long id;
    private int numero;
    private double peso;
    private String remedio;
    private String raca;

    public Boi(){ }

    public Boi(Long id, int numero, double peso, String remedio, String raca) {
        this.id = id;
        this.numero = numero;
        this.peso = peso;
        this.remedio = remedio;
        this.raca = raca;
    }

    public Boi(int numero, double peso, String remedio, String raca) {
        this.numero = numero;
        this.peso = peso;
        this.remedio = remedio;
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

    public String getRemedio() {
        return remedio;
    }

    public void setRemedio(String remedio) {
        this.remedio = remedio;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "Boi{" +
                "id=" + id +
                ", numero=" + numero +
                ", peso=" + peso +
                ", remedio='" + remedio + '\'' +
                ", raca='" + raca + '\'' +
                '}';
    }
}
