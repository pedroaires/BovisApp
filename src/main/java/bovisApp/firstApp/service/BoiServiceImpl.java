package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.exception.BoiExistsException;
import bovisApp.firstApp.exception.BoiNotFoundException;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
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
                () -> new IllegalArgumentException("Numero do boi não pode ser vazio")
        );
        String estadoStr = Optional.ofNullable(boiRequestDTO.getEstadoBoi()).orElseThrow(
                () -> new IllegalArgumentException("Estado do boi não pode ser vazio")
        );
        EstadoBoi estadoBoi = EstadoBoi.getEstadoBoi(estadoStr);
        Raca raca = racaService.getRacaByNome(boiRequestDTO.getRaca());
        Lote lote = loteService.getLoteById(boiRequestDTO.getLoteId());
        if (boiRepository.existsByLoteAndNumero(lote, numero)){
            throw new BoiExistsException(boiRequestDTO.getLoteId(), numero);
        }
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
        Boi boi = boiRepository.findById(id).orElseThrow(() -> new BoiNotFoundException(id));

        Long loteId = boiRequestDTO.getLoteId();
        Integer numero = Optional.ofNullable(boiRequestDTO.getNumero()).orElseThrow(
                () -> new IllegalArgumentException("Numero do boi não pode ser vazio")
        );
        String estadoStr = Optional.ofNullable(boiRequestDTO.getEstadoBoi()).orElseThrow(
                () -> new IllegalArgumentException("Estado do boi não pode ser vazio")
        );
        String racaStr = boiRequestDTO.getRaca();

        Lote lote = loteService.getLoteById(loteId);
        if (boiRepository.existsByLoteAndNumero(lote, numero)){
            throw new BoiExistsException(boiRequestDTO.getLoteId(), numero);
        }
        boi.setNumero(numero);
        boi.setRaca(racaService.getRacaByNome(racaStr));
        boi.setLote(lote);
        boi.setEstadoBoi(EstadoBoi.getEstadoBoi(estadoStr));
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
