package bovisApp.firstApp.service;


import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.model.Boi;

import java.util.List;

public interface BoiService {
    List<Boi> getBois();

    BoiResponseDTO cadastraBoi(BoiRequestDTO boiRequestDTO);
}
