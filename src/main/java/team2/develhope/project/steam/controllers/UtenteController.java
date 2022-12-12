package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Recensione;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;
import team2.develhope.project.steam.services.UserService;

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


    @GetMapping("/libreria")
    public List<Videogioco> libreriaPersonale(@RequestParam Long id ){

        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(utenteRepository.findById(id));

        List<Videogioco> libreria = new ArrayList<>();
        for (Acquisto a : acquisti ) {
            if (a.isIn_libreria()) {
                libreria.add(a.getGioco());
            }
        }
        return libreria;

    }

    @PutMapping("/elimina")
    public void eliminaGioco(@RequestParam Long id ){

        Acquisto acquisto = acquistoRepository.getReferenceById(id);

        acquisto.setIn_libreria(false);

        acquistoRepository.saveAndFlush(acquisto);
    }

    @GetMapping("/recensioni")
    public List<Recensione> visualizzaRecensioniGioco(@RequestParam Long id ){

        List<Acquisto> acquistiByVideogioco = acquistoRepository.findAllByGioco(videogiocoRepository.findById(id));
        List<Recensione> recensioni = new ArrayList<>();

        for ( Acquisto a : acquistiByVideogioco ) {
            recensioni.add(a.getRecensione());
        }
        return recensioni;
    }
}
