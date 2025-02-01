package bovisApp.firstApp.service;


import bovisApp.firstApp.DTO.lote.LoteRequestDTO;
import bovisApp.firstApp.DTO.lote.LoteResponseDTO;
import bovisApp.firstApp.model.Lote;

import java.util.List;

public interface LoteService {

    Lote getLoteById(Long id);

    List<Lote> getLotes();

    Lote cadastraLote(LoteRequestDTO loteRequestDTO);

    Lote editaLote(LoteRequestDTO loteRequestDTO, Long loteId);

    Lote deletaLote(Long id);
}
