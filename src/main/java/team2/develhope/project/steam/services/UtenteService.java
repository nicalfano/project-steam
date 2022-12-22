package team2.develhope.project.steam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import team2.develhope.project.steam.entities.*;
import team2.develhope.project.steam.repositories.AcquistoRepository;
import team2.develhope.project.steam.repositories.RecensioneRepository;
import team2.develhope.project.steam.repositories.UtenteRepository;
import team2.develhope.project.steam.repositories.VideogiocoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Component
public class UtenteService {

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


    public List<Videogioco> libreriaPersonale(Long idUtente) throws Exception {

        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(utenteRepository.findById(idUtente));
        List<Videogioco> libreria = new ArrayList<>();

        try {
            if (acquisti.isEmpty()) {
                throw new Exception();
            }
            for (Acquisto a : acquisti) {
                if (a.isIn_libreria()) {
                    libreria.add(a.getGioco());
                }
            }
        } catch (Exception e1) {
            HttpStatus status = HttpStatus.valueOf("NON HAI ACQUISTATO NESSUN GIOCO ! ");
        }
        try {
            if (libreria.isEmpty()) {
                throw new Exception();
            }

        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("NON HAI NESSUN GIOCO IN LIBRERIA ! SE NE HAI ACQUISTATO QUALCUNO PUOI AGGIUNGERLO QUI ! ");
        }
        return libreria;
    }

    public HttpStatus acquista(Long idUtente, Long idVideogioco) throws Exception {
        Utente utente = utenteRepository.getReferenceById(idUtente);
        Videogioco videogioco = videogiocoRepository.getReferenceById((idVideogioco.longValue()));
        List<Acquisto> acquistos = acquistoRepository.findAll();
        try {
            if (videogioco == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("GIOCO NON DISPONIBILE");
        }

       {
                if (utente.getSaldo() >= videogioco.getPrezzo()) {
                    Acquisto newAcquisto = new Acquisto(utente, videogioco, Timestamp.valueOf(LocalDateTime.now()), true);
                    double saldoAggiornato = utente.getSaldo() - videogioco.getPrezzo();
                    utente.setSaldo(saldoAggiornato);
                    utenteRepository.saveAndFlush(utente);
                    for (Acquisto a:acquistos) {
                        if(a.getGioco().getIdVideogioco() == idVideogioco && a.getUtente().getIdUtente() == idUtente){throw new Exception("Hai già acquistato il titolo");
                    }}
                    acquistoRepository.saveAndFlush(newAcquisto);
                    HttpStatus status = HttpStatus.OK;
                    return status;
                }

        }
        return HttpStatus.OK;
    }

    public void eliminaGioco(Long id) {

        Acquisto acquisto = acquistoRepository.getReferenceById(id);

        try {
            if (acquisto != null) {
                acquisto.setIn_libreria(false);
                acquistoRepository.saveAndFlush(acquisto);
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("NON HAI ACQUISTATO QUESTO GIOCO ! ");
        }
    }

    public List<Recensione> visualizzaRecensioniGioco(Long id) {

        List<Acquisto> acquistiByVideogioco = acquistoRepository.findAllByGioco(videogiocoRepository.findById(id));
        List<Recensione> recensioni = recensioneRepository.findAll();
        List<Recensione> recensioniVideogioco = new ArrayList<>();

        try {
            if (acquistiByVideogioco.isEmpty()) throw new Exception();
            for (Recensione r : recensioni) {
                for (Acquisto a : acquistiByVideogioco)
                    if (r.getAcquisto().getId_Acquisto() == a.getId_Acquisto()) {
                        recensioniVideogioco.add(r);
                    }
            }
        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("QUESTO GIOCO NON E' DISPONIBILE ! ");
        }
        try {
            if (recensioni.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            HttpStatus status = HttpStatus.valueOf("QUESTO GIOCO NON HA RECENSIONI AL MOMENTO !");
        }
        return recensioniVideogioco;
    }

    public void checkUsername(Utente utente) throws Exception {
        if (!userRepository.findUserByUsername(utente.getUsername()).isEmpty()) {
            throw new Exception("The user " + utente.getUsername() + " already exist!");
        }
    }

    public Utente login (LoginDTO loginDTO) {
        Optional<Utente> userToLogin = Optional.ofNullable(userRepository.findUserByEmail(loginDTO.getEmail()));

        if (!userToLogin.isPresent()) {
            throw new RuntimeException("email o password errata!");
        }
        if (userToLogin.get().getPassword().equals(loginDTO.getPassword())) {
            userToLogin.get().setLoginStatus(true);
            return userRepository.save(userToLogin.get());
        }
        else
            throw new RuntimeException("email o password errata!");
        }
    public Utente logout (Long id){
        Optional<Utente> userToLogout = userRepository.findById(id);
        userToLogout.get().setLoginStatus(false);
        return userRepository.save(userToLogout.get());
    }

    //restituisce una lista di tre videogiochi selezionati in base ai generi più giocati dall'utente
    public List<Videogioco> suggerisciVideogiochiGenere(Long idUtente) throws Exception {
        List<Videogioco> libreriaPersonale = libreriaPersonale(idUtente);
        List<String> generiInLibreria = new ArrayList<>();
        for (Videogioco v: libreriaPersonale) {
            generiInLibreria.add(v.getGenere());
            generiInLibreria.add(v.getGenere2());
        }
        Map<String, Integer> frequenzeGeneri = new TreeMap<>();
        Set<String> distinct = new HashSet<>(generiInLibreria);
        for (String s: distinct) {
            frequenzeGeneri.put(s,Collections.frequency(generiInLibreria, s));
            }
        System.out.println(frequenzeGeneri);
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        List<String> treGeneri = new ArrayList<>();
        treGeneri.add(String.valueOf(maxEntry.getKey()));
        frequenzeGeneri.remove(maxEntry.getKey());

        Map.Entry<String, Integer> maxEntry2 = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry2 == null || entry.getValue() > maxEntry2.getValue()) {
                maxEntry2 = entry;
            }
        }
        treGeneri.add(String.valueOf(maxEntry2.getKey()));
        frequenzeGeneri.remove(maxEntry2.getKey());
        Map.Entry<String, Integer> maxEntry3 = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry3 == null || entry.getValue() > maxEntry3.getValue()) {
                maxEntry3 = entry;
            }
        }
        if(maxEntry3 != null) {
            treGeneri.add(String.valueOf(maxEntry3.getKey()));
        }
        List<Videogioco> videogiochiGeneriPiuGiocati = new ArrayList<>();
        for (String g: treGeneri) {
        videogiochiGeneriPiuGiocati.addAll(videogiocoRepository.findAllByGenere(g));
        }

        videogiochiGeneriPiuGiocati.removeAll(libreriaPersonale);

        //mappa dei giochi inerenti ai tre generei più acquistati dall'utente con la media delle recensioni
        Map<Long,Double> mappaGiochiMedia = new HashMap<>();
        for (Videogioco v : videogiochiGeneriPiuGiocati) {
            List<Integer> voti = new ArrayList<>();
        List<Acquisto> aquistiv = acquistoRepository.findAllByGioco(Optional.ofNullable(v));
            int somma = 0;
            double media = 0;
            for (Acquisto c: aquistiv) {
            Recensione r = recensioneRepository.findByAcquisto(c);
            if(r != null)voti.add(r.getVoto());
            }

            for (int voto:voti) { somma += voto;
            }
            if(voti.size() != 0){ media = somma / voti.size();}
            mappaGiochiMedia.put(v.getIdVideogioco(),media);
        }
        //lista di Entry(gioco più media recensioni) ordinati in base alla media
        List<Map.Entry<Long, Double>> giochiOrdinatiMedia = new ArrayList<>(mappaGiochiMedia.entrySet());
        giochiOrdinatiMedia.sort(Map.Entry.comparingByValue());

        List<Videogioco> giochiConsigliatiInBaseAlGenere = new ArrayList<>();
        if(!videogiochiGeneriPiuGiocati.isEmpty()) {
            Optional<Videogioco> giocoConVotoAlto = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 1).getKey());
            Optional<Videogioco> giocoConVotoAlto2 = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 2).getKey());
            Optional<Videogioco> giocoConVotoAlto3 = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 3).getKey());

            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto.get());
            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto2.get());
            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto3.get());
        }

        return giochiConsigliatiInBaseAlGenere;
    }
    //restituisce una lista di tre giochi selezionati in base alle recensioni dell'utente
    public List<Videogioco> suggerisciVideogiochiRecensioni(Long idUtente) throws Exception {
        List<Videogioco> libreriaPersonale = libreriaPersonale(idUtente);
        List<Acquisto> acquistiUtente = acquistoRepository.findAllByUtente(utenteRepository.findById(idUtente));
        List<Recensione> recensioniUtente = new ArrayList<>();
        for (Acquisto a: acquistiUtente) {
        recensioniUtente.add(recensioneRepository.findByAcquisto(a));
        }
        recensioniUtente.sort(Comparator.comparing(Recensione::getVoto));
        //lista dei 3 migliori titoli in base alle recensioni dell'utente
        List<Videogioco> bestInLibreria = new ArrayList<>();
        if(recensioniUtente.size()>= 3) {
            bestInLibreria.add(recensioniUtente.get(recensioniUtente.size() - 1).getAcquisto().getGioco());
            bestInLibreria.add(recensioniUtente.get(recensioniUtente.size() - 2).getAcquisto().getGioco());
            bestInLibreria.add(recensioniUtente.get(recensioniUtente.size() - 3).getAcquisto().getGioco());
        }
        List<String> generiInBest = new ArrayList<>();
        for (Videogioco v: bestInLibreria) {
            generiInBest.add(v.getGenere());
            generiInBest.add(v.getGenere2());
        }
        Map<String, Integer> frequenzeGeneri = new TreeMap<>();
        Set<String> distinct = new HashSet<>(generiInBest);
        for (String s: distinct) {
            frequenzeGeneri.put(s,Collections.frequency(generiInBest, s));
        }

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

            List<String> treGeneri = new ArrayList<>();
        if(maxEntry != null) {
            treGeneri.add(String.valueOf(maxEntry.getKey()));
            frequenzeGeneri.remove(maxEntry.getKey());
        }
        Map.Entry<String, Integer> maxEntry2 = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry2 == null || entry.getValue() > maxEntry2.getValue()) {
                maxEntry2 = entry;
            }
        }
        if(maxEntry2!= null) {
            treGeneri.add(String.valueOf(maxEntry2.getKey()));
            frequenzeGeneri.remove(maxEntry2.getKey());
        }
        Map.Entry<String, Integer> maxEntry3 = null;
        for (Map.Entry<String, Integer> entry : frequenzeGeneri.entrySet()) {
            if (maxEntry3 == null || entry.getValue() > maxEntry3.getValue()) {
                maxEntry3 = entry;
            }
        }
        if(maxEntry3 != null) {
            treGeneri.add(String.valueOf(maxEntry3.getKey()));
        }
        List<Videogioco> videogiochiGeneriPiuGiocati = new ArrayList<>();
        for (String g: treGeneri) {
            videogiochiGeneriPiuGiocati.addAll(videogiocoRepository.findAllByGenere(g));
        }

        videogiochiGeneriPiuGiocati.removeAll(libreriaPersonale);

        //mappa dei giochi inerenti ai tre generei più acquistati dall'utente con la media delle recensioni
        Map<Long,Double> mappaGiochiMedia = new HashMap<>();
        for (Videogioco v : videogiochiGeneriPiuGiocati) {
            List<Integer> voti = new ArrayList<>();
            List<Acquisto> aquistiv = acquistoRepository.findAllByGioco(Optional.ofNullable(v));
            int somma = 0;
            double media = 0;
            for (Acquisto c: aquistiv) {
                Recensione r = recensioneRepository.findByAcquisto(c);
                if(r != null)voti.add(r.getVoto());
            }

            for (int voto:voti) { somma += voto;
            }
            if(voti.size() != 0){ media = somma / voti.size();}
            mappaGiochiMedia.put(v.getIdVideogioco(),media);
        }
        //lista di Entry(gioco più media recensioni) ordinati in base alla media
        List<Map.Entry<Long, Double>> giochiOrdinatiMedia = new ArrayList<>(mappaGiochiMedia.entrySet());
        giochiOrdinatiMedia.sort(Map.Entry.comparingByValue());

        List<Videogioco> giochiConsigliatiInBaseAlGenere = new ArrayList<>();
        if(!videogiochiGeneriPiuGiocati.isEmpty()) {
            Optional<Videogioco> giocoConVotoAlto = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 1).getKey());
            Optional<Videogioco> giocoConVotoAlto2 = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 2).getKey());
            Optional<Videogioco> giocoConVotoAlto3 = videogiocoRepository.findById(giochiOrdinatiMedia.get(giochiOrdinatiMedia.size() - 3).getKey());

            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto.get());
            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto2.get());
            giochiConsigliatiInBaseAlGenere.add(giocoConVotoAlto3.get());
        }


        return giochiConsigliatiInBaseAlGenere;

    }
}