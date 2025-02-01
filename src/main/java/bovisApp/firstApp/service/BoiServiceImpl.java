package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.boi.BoiRequestDTO;
import bovisApp.firstApp.DTO.boi.BoiResponseDTO;
import bovisApp.firstApp.exception.boi.BoiInvalidoException;
import bovisApp.firstApp.exception.boi.BoiJaExisteException;
import bovisApp.firstApp.exception.boi.BoiNaoEncontradoException;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.model.Raca;
import bovisApp.firstApp.model.enumeration.EstadoBoi;
import bovisApp.firstApp.repository.BoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<Boi> getBois() {
        List<Boi> responseList = new ArrayList<>();
        for(Boi boi : boiRepository.findAll()){
            responseList.add(boi);
        }
        return responseList;
    }

    @Override
    public Boi getBoiById(Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(() -> new BoiNaoEncontradoException("Boi não encontrado"));
        return boi;
    }

    @Override
    public Boi cadastraBoi(BoiRequestDTO boiRequestDTO) throws IllegalArgumentException{
        Integer numero = Optional.ofNullable(boiRequestDTO.getNumero()).orElseThrow(
                () -> new BoiInvalidoException("Número do boi não pode ser nulo")
        );

        EstadoBoi estadoBoi;
        try{
            estadoBoi = EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi());
        } catch (IllegalArgumentException e){
            throw new BoiInvalidoException("Estado do boi é inválido. Use 'na_fazenda', 'vendido' ou 'morto'");
        }

        Raca raca = racaService.getRacaByNome(boiRequestDTO.getRaca());
        Lote lote = loteService.getLoteById(boiRequestDTO.getLoteId());
        Optional<Boi> boiOp = boiRepository.findByNumeroAndLoteId(numero, lote.getId());
        if(boiOp.isPresent()){
            throw new BoiJaExisteException("Boi com mesmo número e lote já cadastrado");
        }
        Boi boi = new Boi(numero, raca, lote, estadoBoi);

        boiRepository.save(boi);
        return boi;
    }

    @Override
    public Boi editaBoi(BoiRequestDTO boiRequestDTO, Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(() -> new BoiNaoEncontradoException("Boi não encontrado"));
        Integer numero = Optional.ofNullable(boiRequestDTO.getNumero()).orElseThrow(
                () -> new BoiInvalidoException("Número do boi não pode ser nulo")
        );
        EstadoBoi estadoBoi;
        try{
            estadoBoi = EstadoBoi.getEstadoBoi(boiRequestDTO.getEstadoBoi());
        } catch (IllegalArgumentException e){
            throw new BoiInvalidoException("Estado do boi é inválido. Use 'na_fazenda', 'vendido' ou 'morto'");
        }
        Raca raca = racaService.getRacaByNome(boiRequestDTO.getRaca());
        Lote lote = loteService.getLoteById(boiRequestDTO.getLoteId());

        boi.setNumero(numero);
        boi.setRaca(raca);
        boi.setLote(lote);
        boi.setEstadoBoi(estadoBoi);
        boiRepository.save(boi);
        return boi;
    }

    @Override
    public Boi deleteBoi(Long id){
        Boi boi = boiRepository.findById(id).orElseThrow(() -> new BoiNaoEncontradoException("Boi não encontrado"));
        boiRepository.delete(boi);
        return boi;
    }


}
