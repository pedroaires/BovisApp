package bovisApp.firstApp.service;

import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.repository.BoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoiServiceImpl implements BoiService {
    private final BoiRepository boiRepository;

    @Autowired
    public BoiServiceImpl(BoiRepository boiRepository) {
        this.boiRepository = boiRepository;
    }

    @Override
    public List<Boi> getBois() {
        return boiRepository.findAll();
    }
}
