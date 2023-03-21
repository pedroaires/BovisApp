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

    private final RacaRepository racaRepository;

    private final LoteRepository loteRepository;


    @Autowired
    public BoiServiceImpl(BoiRepository boiRepository, RacaRepository racaRepository, LoteRepository loteRepository) {
        this.boiRepository = boiRepository;
        this.racaRepository = racaRepository;
        this.loteRepository = loteRepository;
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
        Raca raca = getRacaByNome(normalizaRaca(boiRequestDTO.getRaca()));
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

    private String normalizaRaca(String raca){
        //TODO
        return raca;
    }

    // Se a raça não existir, cria uma nova
    private Raca getRacaByNome(String racaStr){
        Optional<Raca> racaOp = racaRepository.findByNome(racaStr);
        Raca raca;
        if(racaOp.isPresent()){
            raca = racaOp.get();
        }
        else{
            raca = new Raca(racaStr);
            racaRepository.save(raca);
        }
        return raca;
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
