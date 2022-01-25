package bovisApp.firstApp.controller;

import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.service.BoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class BoiApiController {
    private final BoiService boiService;

    @Autowired
    public BoiApiController(BoiService boiService) {
        this.boiService = boiService;
    }

    @GetMapping
    public List<Boi> getBoi(){
        return boiService.getBois();
    }

}
