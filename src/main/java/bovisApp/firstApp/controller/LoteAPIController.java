package bovisApp.firstApp.controller;


import bovisApp.firstApp.DTO.lote.LoteResponseDTO;
import bovisApp.firstApp.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/lote")
public class LoteAPIController {
    private final LoteService loteService;

    @Autowired
    public LoteAPIController(LoteService loteService) {
        this.loteService = loteService;
    }

    @GetMapping(value = "/listLotes")
    @ResponseStatus(HttpStatus.OK)
    public List<LoteResponseDTO> getLotes(){
        List<LoteResponseDTO> responseList = loteService.getLotes().stream().map(LoteResponseDTO::new).collect(Collectors.toList());
        return responseList;
    }

    @GetMapping(value = "/getLoteById")
    @ResponseStatus(HttpStatus.OK)
    public LoteResponseDTO getLoteById(@RequestParam Long id){
        LoteResponseDTO response = new LoteResponseDTO(loteService.getLoteById(id));
        return response;
    }

//    @PostMapping(value = "/cadastraLote")
//    @ResponseStatus(HttpStatus.CREATED)
//    public LoteResponseDTO cadastrarLote(@RequestBody LoteRequestDTO loteRequestDTO){
//        LoteResponseDTO loteResponseDTO = loteService.cadastraLote(loteRequestDTO);
//        return loteResponseDTO;
//    }
//
//    @PostMapping(value = "/editaLote")
//    @ResponseStatus(HttpStatus.OK)
//    public LoteResponseDTO editaLote(@RequestBody LoteRequestDTO loteRequestDTO, @RequestParam Long loteId){
//        LoteResponseDTO loteResponseDTO = loteService.editaLote(loteRequestDTO, loteId);
//        return loteResponseDTO;
//    }
//
//    @DeleteMapping(value = "/deletaLote")
//    @ResponseStatus(HttpStatus.OK)
//    public LoteResponseDTO deletaLote(@RequestParam Long id){
//        LoteResponseDTO loteResponseDTO = loteService.deleteLote(id);
//        return loteResponseDTO;
//    }
}
