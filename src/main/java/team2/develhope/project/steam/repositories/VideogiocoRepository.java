package team2.develhope.project.steam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team2.develhope.project.steam.entities.Videogioco;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco,Long> {
    public Videogioco findByTitolo (String titolo);

    @Query(value = "SELECT * FROM Videogioco v WHERE v.titolo LIKE %?1%", nativeQuery = true)
    Collection<Videogioco> findAllVideogiocoLikeTitolo(String titolo);

    @Query(value = "SELECT * FROM Videogioco v WHERE v.genere OR v.genere2 = ?1", nativeQuery = true)
    Collection<Videogioco> findAllByGenere(String genere);
}
