package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.develhope.project.steam.entities.Videogioco;

import java.util.List;

public interface VideogiocoRepository extends JpaRepository<Videogioco,Long> {
    public Videogioco findByTitolo (String titolo);
}
