package bovisApp.firstApp.service;


import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.model.Boi;

import java.util.List;

public interface BoiService {
    List<Boi> getBois();

    Boi getBoiById(Long id);

    Boi cadastraBoi(BoiRequestDTO boiRequestDTO);

    Boi editaBoi(BoiRequestDTO boiRequestDTO, Long boiId);

    Boi deleteBoi(Long id);
}
