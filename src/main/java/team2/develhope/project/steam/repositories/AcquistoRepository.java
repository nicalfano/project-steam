package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.develhope.project.steam.entities.Acquisto;
import team2.develhope.project.steam.entities.Utente;

import java.util.List;
import java.util.Optional;

public interface AcquistoRepository extends JpaRepository<Acquisto,Long> {

    public List<Acquisto> findAllByUtente(Optional<Utente> utente);



}
