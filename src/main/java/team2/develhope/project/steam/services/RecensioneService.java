package team2.develhope.project.steam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;

import java.util.Collections;
import java.util.List;

@Service
@Component
public class RecensioneService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private AcquistoRepository acquistoRepository;


    //Optional Acquisto??
    public Acquisto getAcquisto(long idUtente, Long idVideogioco) throws Exception {
        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(utenteRepository.findById(idUtente));
        for (Acquisto singoloAcquisto : acquisti) {
            if (idVideogioco.equals(singoloAcquisto.getGioco().getIdVideogioco())){
                return singoloAcquisto;
            }
        }
        throw new Exception("non c'è");
    }

    /*
    public Acquisto checkAcquisto(Long idUtente, Long idVideogioco){
        if (!acquistoRepository.existsById(idUtente) && !acquistoRepository.existsById(idVideogioco)){
            System.out.println("Non puoi scrivere una recensione in quanto non hai effettuato l'acquisto del gioco. Duh!");
            throw new RuntimeException("");
        }else{
            System.out.println("Inserire voto e recensione...");
        }
        return acquistoRepository.getSingoloAcquisto(idUtente, idVideogioco);
    }*/
}
