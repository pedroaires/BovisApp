package bovisApp.firstApp.DTO.boi;

import bovisApp.firstApp.model.Boi;

public class BoiResponseDTO {
    private int numero;

    private Long loteId;
    private String estadoBoi;
    private String raca;

    public BoiResponseDTO(Boi boi){
        this.numero = boi.getNumero();
        this.loteId = boi.getLote().getId();
        this.estadoBoi = boi.getEstadoBoi().toString();
        this.raca = boi.getRaca().getNome();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
