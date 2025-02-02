package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.pesagem.PesagemRequestDTO;
import bovisApp.firstApp.model.Pesagem;

import java.util.List;

public interface PesagemService {
    List<Pesagem> getPesagens();

    List<Pesagem> getPesagemByBoiId(Long boiId);

    Pesagem cadastraPesagem(PesagemRequestDTO pesagemRequestDTO);
}
