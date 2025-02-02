package bovisApp.firstApp.controller;

import bovisApp.firstApp.DTO.pesagem.PesagemRequestDTO;
import bovisApp.firstApp.DTO.pesagem.PesagemResponseDTO;
import bovisApp.firstApp.model.Pesagem;
import bovisApp.firstApp.service.PesagemService;
import bovisApp.firstApp.service.PesagemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/pesagem")
public class PesagemAPIController {
    private final PesagemService pesagemService;

    @Autowired
    public PesagemAPIController(PesagemService pesagemService) {
        this.pesagemService = pesagemService;
    }

    @GetMapping(value = "/listPesagens")
    public List<PesagemResponseDTO> listPesagens(){
        List<PesagemResponseDTO> pesagens = pesagemService.getPesagens().stream().map(PesagemResponseDTO::new).collect(Collectors.toList());
        return pesagens;
    }

    @GetMapping(value = "/getPesagemByBoiId")
    public List<PesagemResponseDTO> getPesagemByBoiId(Long boiId){
        List<PesagemResponseDTO> pesagemResponseDTO = pesagemService.getPesagemByBoiId(boiId).stream().map(PesagemResponseDTO::new).collect(Collectors.toList());
        return pesagemResponseDTO;
    }

    @PostMapping(value = "/cadastraPesagem")
    public PesagemResponseDTO cadastraPesagem(PesagemRequestDTO pesagemRequestDTO){
        PesagemResponseDTO pesagemResponseDTO = new PesagemResponseDTO(pesagemService.cadastraPesagem(pesagemRequestDTO));
        return pesagemResponseDTO;
    }

    @PutMapping(value = "/editaPesagem")
    public PesagemResponseDTO editaPesagem(PesagemRequestDTO pesagemRequestDTO, Long pesagemId){
        PesagemResponseDTO pesagemResponseDTO = new PesagemResponseDTO(pesagemService.editaPesagem(pesagemRequestDTO, pesagemId));
        return pesagemResponseDTO;
    }

    @DeleteMapping(value = "/deletaPesagem")
    public PesagemResponseDTO deletaPesagem(Long id){
        PesagemResponseDTO pesagemResponseDTO = new PesagemResponseDTO(pesagemService.deletaPesagem(id));
        return pesagemResponseDTO;
    }
}
