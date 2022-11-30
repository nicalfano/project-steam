package team2.develhope.project.steam.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Recensione {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private long idVideogioco;
    @Column(nullable = false)
    private long idUtente;
    @Column(nullable = false)
    private int voto;
    private String commento;




    @Override
    public String toString() {
        return "Recensione{" +
                "idVideogioco=" + idVideogioco +
                ", idUtente=" + idUtente +
                ", voto=" + voto +
                ", commento='" + commento + '\'' +
                '}';
    }
}
