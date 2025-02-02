package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.pesagem.PesagemRequestDTO;
import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Medicacao;
import bovisApp.firstApp.model.Pesagem;
import bovisApp.firstApp.repository.PesagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PesagemServiceImpl implements PesagemService{
    private final PesagemRepository pesagemRepository;

    private BoiService boiService;

    private MedicacaoService medicacaoService;

    @Autowired
    public PesagemServiceImpl(PesagemRepository pesagemRepository, BoiService boiService, MedicacaoService medicacaoService) {
        this.pesagemRepository = pesagemRepository;
        this.boiService = boiService;
        this.medicacaoService = medicacaoService;
    }

    @Override
    public List<Pesagem> getPesagens(){
        List<Pesagem> responseList = pesagemRepository.findAll();
        return responseList;
    }

    @Override
    public List<Pesagem> getPesagemByBoiId(Long boiId){
        List<Pesagem> pesagem = pesagemRepository.findByBoiId(boiId);
        return pesagem;
    }

    @Override
    public Pesagem cadastraPesagem(PesagemRequestDTO pesagemRequestDTO) {
        Double peso = Optional.ofNullable(pesagemRequestDTO.getPeso()).orElseThrow(() -> new IllegalArgumentException("Peso não pode ser nulo"));
        Date data = Optional.ofNullable(pesagemRequestDTO.getData()).orElseThrow(() -> new IllegalArgumentException("Data da pesagem não pode ser nula"));
        String descricao = Optional.ofNullable(pesagemRequestDTO.getDescricao()).orElse("");
        Long boiId = Optional.ofNullable(pesagemRequestDTO.getBoiId()).orElseThrow(() -> new IllegalArgumentException("Id do boi não pode ser nulo"));
        List<String> medicacoesStr = Optional.ofNullable(pesagemRequestDTO.getMedicacoes()).orElse(null);
        List<Medicacao> medicacoes = medicacoesStr.stream().map(medicacaoService::getOrCreateMedicacao).collect(Collectors.toList());

        Boi boi = boiService.getBoiById(boiId);
        Pesagem pesagem = new Pesagem(boi, data, peso, descricao, medicacoes);
        pesagemRepository.save(pesagem);
        return pesagem;
    }
    @Override
    public Pesagem editaPesagem(PesagemRequestDTO pesagemRequestDTO, Long pesagemId) {
        if (pesagemId == null) {
            throw new IllegalArgumentException("Id da pesagem não pode ser nulo");
        }
        Pesagem pesagem = pesagemRepository.findById(pesagemId).orElseThrow(() -> new EntityNotFoundException("Pesagem não encontrada"));
        Double peso = Optional.ofNullable(pesagemRequestDTO.getPeso()).orElseThrow(() -> new IllegalArgumentException("Peso não pode ser nulo"));
        Date data = Optional.ofNullable(pesagemRequestDTO.getData()).orElseThrow(() -> new IllegalArgumentException("Data da pesagem não pode ser nula"));
        String descricao = Optional.ofNullable(pesagemRequestDTO.getDescricao()).orElse("");
        Long boiId = Optional.ofNullable(pesagemRequestDTO.getBoiId()).orElseThrow(() -> new IllegalArgumentException("Id do boi não pode ser nulo"));
        List<String> medicacoesStr = Optional.ofNullable(pesagemRequestDTO.getMedicacoes()).orElse(null);
        List<Medicacao> medicacoes = medicacoesStr.stream().map(medicacaoService::getOrCreateMedicacao).collect(Collectors.toList());

        Boi boi = boiService.getBoiById(boiId);
        pesagem.setData(data);
        pesagem.setPeso(peso);
        pesagem.setDescricao(descricao);
        pesagem.setMedicacoes(medicacoes);
        pesagem.setBoi(boi);
        pesagemRepository.save(pesagem);
        return pesagem;
    }

    @Override
    public Pesagem deletaPesagem(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id da pesagem não pode ser nulo");
        }
        Pesagem pesagem = pesagemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pesagem não encontrada"));
        pesagemRepository.delete(pesagem);
        return pesagem;
    }



}
