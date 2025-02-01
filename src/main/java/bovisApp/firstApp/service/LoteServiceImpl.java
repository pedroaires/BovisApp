package bovisApp.firstApp.service;

import bovisApp.firstApp.DTO.lote.LoteRequestDTO;
import bovisApp.firstApp.exception.lote.LoteInvalidoException;
import bovisApp.firstApp.exception.lote.LoteNaoEncontradoException;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteServiceImpl implements LoteService{
    private final LoteRepository loteRepository;

    @Autowired
    public LoteServiceImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public Lote getLoteById(Long id){
        if(id == null){
            Lote lote = new Lote();
            lote.setDescricao("Lote criado automaticamente para boi sem lote");
            loteRepository.save(lote);
            return lote;
        }
        Optional<Lote> loteOp = loteRepository.findById(id);
        return loteOp.orElseThrow(() -> new LoteNaoEncontradoException("Lote não encontrado"));
    }

    @Override
    public List<Lote> getLotes(){
        return loteRepository.findAll();
    }

    @Override
    public Lote cadastraLote(LoteRequestDTO loteRequestDTO){
        Lote lote = new Lote();
        lote.setDataCompra(loteRequestDTO.getDataCompra());
        lote.setDataVenda(loteRequestDTO.getDataVenda());
        if(lote.getDataVenda() != null){
            if (lote.getDataCompra() == null){
                throw new LoteInvalidoException("Data de compra não pode ser nula quando data de venda não é nula");
            }
            if (lote.getDataVenda().before(lote.getDataCompra())) {
                throw new LoteInvalidoException("Data de venda não pode ser anterior a data de compra");
            }
        }
        lote.setEstado(loteRequestDTO.getEstadoLote());
        lote.setDescricao(loteRequestDTO.getDescricao());
        loteRepository.save(lote);
        return lote;
    }

    @Override
    public Lote editaLote(LoteRequestDTO loteRequestDTO, Long loteId){
        if (loteId == null){
            throw new LoteInvalidoException("Id do lote não pode ser nulo");
        }
        Optional<Lote> loteOp = loteRepository.findById(loteId);
        Lote lote = loteOp.orElseThrow(() -> new LoteNaoEncontradoException("Lote não encontrado"));
        if(loteRequestDTO.getDataVenda() != null){
            if (loteRequestDTO.getDataCompra() == null){
                throw new LoteInvalidoException("Data de compra não pode ser nula quando data de venda não é nula");
            }
            if (loteRequestDTO.getDataVenda().before(loteRequestDTO.getDataCompra())) {
                throw new LoteInvalidoException("Data de venda não pode ser anterior a data de compra");
            }
        }
        lote.setDataCompra(loteRequestDTO.getDataCompra());
        lote.setDataVenda(loteRequestDTO.getDataVenda());
        lote.setEstado(loteRequestDTO.getEstadoLote());
        lote.setDescricao(loteRequestDTO.getDescricao());
        loteRepository.save(lote);
        return lote;
    }

    @Override
    public Lote deletaLote(Long id){
        if (id == null){
            throw new LoteInvalidoException("Id do lote não pode ser nulo");
        }
        Lote lote = loteRepository.findById(id).orElseThrow(() -> new LoteNaoEncontradoException("Lote não encontrado"));
        loteRepository.delete(lote);
        return lote;
    }


}
