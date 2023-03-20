package bovisApp.firstApp.model.enumeration;

public enum EstadoBoi {
    VENDIDO,
    NA_FAZENDA,
    MORTO;

    public static EstadoBoi getEstadoBoi(String estadoBoiStr) {
        if(estadoBoiStr == null || estadoBoiStr.isEmpty()) {
            throw new IllegalArgumentException("Estado do boi não pode ser nulo ou vazio");
        }
        String strNorm = estadoBoiStr.toUpperCase().trim();
        return EstadoBoi.valueOf(strNorm);
    }
}