package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoiServiceImpl implements BoiService {
    private final BoiRepository boiRepository;
    private final LoteService loteService;
    private final RacaService racaService;

    @Autowired
    public BoiServiceImpl(BoiRepository boiRepository, LoteService loteService, RacaService racaService) {
        this.boiRepository = boiRepository;
        this.loteService = loteService;
        this.racaService = racaService;
    }

    @Override
    public List<BoiResponseDTO> getBois() {
        List<BoiResponseDTO> responseList = new ArrayList<>();
        for(Boi boi : boiRepository.findAll()){
            responseList.add(new BoiResponseDTO(boi));
        }
        return responseList;
    }

    @Override
    public BoiResponseDTO cadastraBoi(BoiRequestDTO boiRequestDTO) throws IllegalArgumentException{
        Integer numero = Optional.ofNullable(boiRequestDTO.getNumero()).orElseThrow(
                () -> new IllegalArgumentException("Estado do boi e numero nao podem ser vazios")
        );
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi());
        Raca raca = racaService.getRacaByNome(boiRequestDTO.getRaca());
        Lote lote = loteService.getLoteById(boiRequestDTO.getLoteId());
        Boi boi = new Boi(
                numero,
                raca,
                lote,
                estadoBoi
        );
        boiRepository.save(boi);
        return new BoiResponseDTO(boi);
    }

    @Override
    public BoiResponseDTO editaBoi(BoiRequestDTO boiRequestDTO, Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        boi.setNumero(boiRequestDTO.getNumero());
        boi.setRaca(racaService.getRacaByNome(boiRequestDTO.getRaca()));
        boi.setLote(loteService.getLoteById(boiRequestDTO.getLoteId()));
        boi.setEstadoBoi(EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi()));
        boiRepository.save(boi);
        return new BoiResponseDTO(boi);
    }

    @Override
    public BoiResponseDTO deleteBoi(Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        boiRepository.delete(boi);
        return new BoiResponseDTO(boi);
    }


}
