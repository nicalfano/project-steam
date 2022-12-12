package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
    public HttpStatus acquista(@RequestParam Long idUtente, @RequestParam Long idVideogioco ) throws Exception {

        Utente utente = utenteRepository.getReferenceById(idUtente);
        Videogioco videogioco = videogiocoRepository.getReferenceById((idVideogioco.longValue()));

        if (utente.getSaldo() >= videogioco.getPrezzo() ) {
            Acquisto newAcquisto = new Acquisto(utente,videogioco, Timestamp.valueOf(LocalDateTime.now()),true);
            double saldoAggiornato = utente.getSaldo() - videogioco.getPrezzo();
            utente.setSaldo(saldoAggiornato);
            utenteRepository.saveAndFlush(utente);
            acquistoRepository.saveAndFlush(newAcquisto);
            HttpStatus status = HttpStatus.OK;
            return status;
        } else {
            throw new Exception("Saldo insufficiente ! ");
        }
    }
}
