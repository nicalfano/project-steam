package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Recensione;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;
import team2.develhope.project.steam.services.UserService;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utente")
public class UtenteController {


    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @PostMapping("/registrazione")
    public Utente registraUtente(@RequestBody Utente utente) throws Exception {
        userService.checkUsername(utente);
        System.out.println("The user is been saved!");
        return utenteRepository.save(utente);
    }

    @PostMapping("/acquista")
    public HttpStatus acquista(@RequestParam Long idUtente, @RequestParam Long idVideogioco) throws Exception {
        return userService.acquista(idUtente, idVideogioco);
    }

    @GetMapping("/libreria")
    public List<Videogioco> libreriaPersonale(@RequestParam Long id) throws Exception {
        return userService.libreriaPersonale(id);
    }

    @PutMapping("/elimina")
    public void eliminaGioco(@RequestParam Long id) {
        userService.eliminaGioco(id);
    }

    @GetMapping("/recensioni")
    public List<Recensione> visualizzaRecensioniGioco(@RequestParam Long id) {
        return userService.visualizzaRecensioniGioco(id);
    }

}



