package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Recensione;
import team2.develhope.project.steam.entities.Utente;
import team2.develhope.project.steam.entities.Videogioco;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {


    Recensione findByAcquisto(Acquisto c);
}
