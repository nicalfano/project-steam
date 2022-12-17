package team2.develhope.project.steam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team2.develhope.project.steam.entities.*;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;
import team2.develhope.project.steam.services.UtenteService;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController {


    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @GetMapping("/consigliati")
    public ConsigliatiRTO advices(@RequestParam Long idUtente) throws Exception {
        ConsigliatiRTO consigliatiRTO = new ConsigliatiRTO();
        consigliatiRTO.setPrimaLista(utenteService.suggerisciVideogiochiGenere(idUtente));
        consigliatiRTO.setSecondaLista(utenteService.suggerisciVideogiochiRecensioni(idUtente));
        return consigliatiRTO;
    }
    @GetMapping("/libreria")
    public List<Videogioco> libreriaPersonale(@RequestParam Long id) throws Exception {
        return utenteService.libreriaPersonale(id);
    }
    @GetMapping("/recensioni")
    public List<Recensione> visualizzaRecensioniGioco(@RequestParam Long id) {
        return utenteService.visualizzaRecensioniGioco(id);
    }

    @PostMapping("/registrazione")
    public Utente registraUtente(@RequestBody Utente utente) throws Exception {
        utenteService.checkUsername(utente);
        System.out.println("The user is been saved!");
        return utenteRepository.save(utente);
    }

    @PostMapping("/acquista")
    public HttpStatus acquista(@RequestParam Long idUtente, @RequestParam Long idVideogioco) throws Exception {
        return utenteService.acquista(idUtente, idVideogioco);
    }

    @PutMapping("/elimina")
    public void eliminaGioco(@RequestParam Long id) {
        utenteService.eliminaGioco(id);
    }



    @PutMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        utenteService.login(loginDTO);
        return "You are logged in, welcome back!";
    }

    @PutMapping("/logout")
    public String logout(@RequestParam Long idUtente) {
        utenteService.logout(idUtente);
        return "You are logged out, bye!";
    }

}



