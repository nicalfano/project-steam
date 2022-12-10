package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.develhope.project.steam.entities.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
