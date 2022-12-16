package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team2.develhope.project.steam.entities.Utente;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

   public List<Utente> findUserByUsername(String username);

   Utente findUserByEmail(String email);
}
