package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import bovisApp.firstApp.repository.LoteRepository;
import bovisApp.firstApp.repository.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoiServiceImpl implements BoiService {
    private final BoiRepository boiRepository;
    private final LoteRepository loteRepository;
    private final RacaService racaService;

    @Autowired
    public BoiServiceImpl(BoiRepository boiRepository, LoteRepository loteRepository, RacaService racaService) {
        this.boiRepository = boiRepository;
        this.loteRepository = loteRepository;
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
    public BoiResponseDTO cadastraBoi(BoiRequestDTO boiRequestDTO){
        Raca raca = racaService.getRacaByNome(boiRequestDTO.getRaca());
        Lote lote = getLoteById(boiRequestDTO.getLoteId());
        Boi boi = new Boi(
                boiRequestDTO.getNumero(),
                raca,
                lote,
                EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi())
        );
        boiRepository.save(boi);
        return new BoiResponseDTO(boi);
    }

    @Override
    public BoiResponseDTO editaBoi(BoiRequestDTO boiRequestDTO, Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        boi.setNumero(boiRequestDTO.getNumero());
        boi.setRaca(racaService.getRacaByNome(boiRequestDTO.getRaca()));
        boi.setLote(getLoteById(boiRequestDTO.getLoteId()));
        boi.setEstadoBoi(EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi()));
        boiRepository.save(boi);
        return new BoiResponseDTO(boi);
    }

    private Lote getLoteById(Long id){

        if(id == null){
            Lote lote = new Lote();
            loteRepository.save(lote);
            return lote;
        }
        Optional<Lote> loteOp = loteRepository.findById(id);
        return loteOp.orElseThrow(() -> new EntityNotFoundException("Lote não encontrado"));
    }

}
