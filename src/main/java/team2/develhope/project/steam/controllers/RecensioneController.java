package team2.develhope.project.steam.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Recensione;
import team2.develhope.project.steam.entities.RecensioneDTO;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.services.RecensioneService;

import java.util.List;

@RestController
@RequestMapping("/recensione")
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;

    @PostMapping("/pubblica")
    public Recensione recensisciGioco(@RequestParam Long idUtente, @RequestParam Long idVideogioco, @RequestBody RecensioneDTO recensione) throws Exception {

        Acquisto acquisto = recensioneService.getAcquisto(idUtente, idVideogioco);

        Recensione recensione1 = new Recensione(acquisto, recensione.getVoto(), recensione.getCommento());

        return recensioneRepository.save(recensione1);
    }


}
