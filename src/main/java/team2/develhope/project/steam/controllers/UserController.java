package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/utente")
public class UserController {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;
    @PostMapping
    public Utente inserisciUtente (@RequestBody Utente utente){
        return utenteRepository.save(utente);

    }

    @GetMapping("/libreria")
    public List<Videogioco> libreriaPersonale(@RequestParam Long id ){

        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(utenteRepository.findById(id));

        List<Videogioco> libreria = new ArrayList<>();
        for (Acquisto a : acquisti ) {
            libreria.add(a.getGioco());

        }
        return libreria;
    }
}
