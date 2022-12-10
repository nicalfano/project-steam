package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/acquisto")
public class AcquistoController {

    @Autowired
    private AcquistoRepository acquistoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private VideogiocoRepository videogiocoRepository;
    @PostMapping
    public Acquisto acquista(@RequestParam Long idUtente, @RequestParam Long idVideogioco){

        Utente utente = utenteRepository.getReferenceById(idUtente);
        Videogioco videogioco = videogiocoRepository.getReferenceById((idVideogioco.longValue()));
        Acquisto newAcquisto = new Acquisto(utente,videogioco, Timestamp.valueOf(LocalDateTime.now()),true);
        return acquistoRepository.save(newAcquisto);
    }
}
