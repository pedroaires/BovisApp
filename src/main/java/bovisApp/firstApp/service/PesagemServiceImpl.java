package bovisApp.firstApp.service;

import bovisApp.firstApp.model.Pesagem;
import bovisApp.firstApp.repository.PesagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PesagemServiceImpl implements PesagemService{
    private final PesagemRepository pesagemRepository;

    @Autowired
    public PesagemServiceImpl(PesagemRepository pesagemRepository) {
        this.pesagemRepository = pesagemRepository;
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
}
