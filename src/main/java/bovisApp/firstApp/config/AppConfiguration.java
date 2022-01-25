package bovisApp.firstApp.config;

import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import bovisApp.firstApp.repository.BoiRepository;
import bovisApp.firstApp.repository.LoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Date;
import java.util.List;



@Configuration
public class AppConfiguration {

    @Bean
    CommandLineRunner commandLineRunner (BoiRepository boiRepository, LoteRepository loteRepository){
        return args-> {
            Boi boi1 = new Boi(
                    1,
                    120,
                    "novalgina",
                    "nelore"
            );

            Boi boi2 = new Boi(
                    2,
                    250,
                    "novalgina",
                    "nelore"
            );

            Boi boi3 = new Boi(
                    3,
                    300,
                    "novalgina",
                    "angus"
            );

            Boi boi4 = new Boi(
                    4,
                    400,
                    "novalgina",
                    "nelore"
            );

            boiRepository.saveAll(List.of(boi1, boi2, boi3, boi4));

            Lote lote1 = new Lote(
                    new Date(2020, 02, 01),
                    null,
                    List.of(boi1, boi2, boi3),
                    20000,
                    500
            );

            Lote lote2 = new Lote(
                    new Date(2022, 10, 25),
                    null,
                    List.of(boi4),
                    6000,
                    250
            );
            loteRepository.saveAll(List.of(lote1, lote2));
        };
    }
}
