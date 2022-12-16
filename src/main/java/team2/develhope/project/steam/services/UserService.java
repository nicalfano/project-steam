package team2.develhope.project.steam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Recensione;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class UserService {

    @Autowired
    private UtenteRepository userRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;

    @Autowired
    private VideogiocoRepository videogiocoRepository;


    public List<Videogioco> libreriaPersonale( Long id ) throws Exception {

        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(utenteRepository.findById(id));
        List<Videogioco> libreria = new ArrayList<>();

        try {
            if ( acquisti.isEmpty() ) {
                throw new Exception();
            }
            for (Acquisto a : acquisti) {
                if (a.isIn_libreria()) {
                    libreria.add(a.getGioco());
                }
            }
        } catch (Exception e1 ) {
            HttpStatus status = HttpStatus.valueOf("NON HAI ACQUISTATO NESSUN GIOCO ! ");
        }
        try {
            if (libreria.isEmpty()) { throw new Exception(); }

        } catch ( Exception e ) {
            HttpStatus status = HttpStatus.valueOf("NON HAI NESSUN GIOCO IN LIBRERIA ! SE NE HAI ACQUISTATO QUALCUNO PUOI AGGIUNGERLO QUI ! ");
        }
        return libreria;
    }

    public HttpStatus acquista( Long idUtente, Long idVideogioco ) throws Exception {

        Utente utente = utenteRepository.getReferenceById(idUtente);
        Videogioco videogioco = videogiocoRepository.getReferenceById((idVideogioco.longValue()));

        try {
            if(videogioco == null ) { throw new Exception(); }
        } catch (Exception e ) {
            HttpStatus status = HttpStatus.valueOf("GIOCO NON DISPONIBILE");
        }

        try {
            if(utente == null ) { throw new Exception(); }

            else {
                if (utente.getSaldo() >= videogioco.getPrezzo() ) {
                    Acquisto newAcquisto = new Acquisto(utente,videogioco, Timestamp.valueOf(LocalDateTime.now()),true);
                    double saldoAggiornato = utente.getSaldo() - videogioco.getPrezzo();
                    utente.setSaldo(saldoAggiornato);
                    utenteRepository.saveAndFlush(utente);
                    acquistoRepository.saveAndFlush(newAcquisto);
                    HttpStatus status = HttpStatus.OK;
                    return status;
                } else {
                    throw new Exception(" SALDO INSUFFICIENTE ! ");
                }
            }
        } catch (Exception e ) {
            HttpStatus status = HttpStatus.valueOf("NON PUOI FARE ACQUISTI SE NON SEI REGISTRATO ! ");
        }
        return HttpStatus.OK;
    }

    public void eliminaGioco( Long id ){

        Acquisto acquisto = acquistoRepository.getReferenceById(id);

        try {
            if (acquisto != null ) {
                acquisto.setIn_libreria(false);
                acquistoRepository.saveAndFlush(acquisto);
            } else { throw new Exception(); }

        } catch ( Exception e) {
            HttpStatus status = HttpStatus.valueOf("NON HAI ACQUISTATO QUESTO GIOCO ! ");
        }
    }

    public List<Recensione> visualizzaRecensioniGioco( Long id ) {

        List<Acquisto> acquistiByVideogioco = acquistoRepository.findAllByGioco(videogiocoRepository.findById(id));
        List<Recensione> recensioni = recensioneRepository.findAll();
        List<Recensione> recensioniVideogioco = new ArrayList<>();

        try {
            if ( acquistiByVideogioco.isEmpty() ) throw new Exception();
            for ( Recensione r : recensioni ) {
                for ( Acquisto a : acquistiByVideogioco )
                    if ( r.getAcquisto().getId_Acquisto() == a.getId_Acquisto() ) {
                        recensioniVideogioco.add(r);
                    }
            }
        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("QUESTO GIOCO NON E' DISPONIBILE ! ");
        }
        try {
            if (recensioni.isEmpty()) { throw new Exception(); }
        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("QUESTO GIOCO NON HA RECENSIONI AL MOMENTO !");
        }
        return recensioniVideogioco;
    }

    public void checkUsername(Utente utente) throws Exception {
        if (!userRepository.findUserByUsername(utente.getUsername()).isEmpty()){
            throw new Exception("The user " + utente.getUsername() + " already exist!");
        }
    }
}
