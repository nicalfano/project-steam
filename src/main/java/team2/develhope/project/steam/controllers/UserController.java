package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.UtenteRepository;

@RestController
@RequestMapping("/utente")
public class UserController {
    @Autowired
    private UtenteRepository utenteRepository;
    @PostMapping
    public Utente inserisciUtente (@RequestBody Utente utente){
        return utenteRepository.save(utente);
    }
}
