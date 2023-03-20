package bovisApp.firstApp.DTO.boi;

public class BoiRequestDTO {
    private int numero;
    private String raca;
    private Long loteId;

    private String estadoBoi;

    public BoiRequestDTO(int numero, String raca, Long loteId, String estadoBoi) {
        this.numero = numero;
        this.raca = raca;
        this.loteId = loteId;
        this.estadoBoi = estadoBoi;
    }
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getEstadoBoi() {
        return estadoBoi;
    }

    public void setEstadoBoi(String estadoBoi) {
        this.estadoBoi = estadoBoi;
    }
}
