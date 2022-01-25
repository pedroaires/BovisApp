package bovisApp.firstApp.service;

import bovisApp.firstApp.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoteServiceImpl implements  LoteService{
    private final LoteRepository loteRepository;

    @Autowired
    public LoteServiceImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }
}
