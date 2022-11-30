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
    private long id_Recensione;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Acquisto acquisto;
    @Column(nullable = false)
    private int voto;
    private String commento;

    public Recensione(Acquisto acquisto, int voto, String commento){
        this.acquisto = acquisto;
        this.voto = voto;
        this.commento = commento;
    }


    @Override
    public String toString() {
        return "Recensione{" +
                "Acquisto=" + acquisto +
                ", voto=" + voto +
                ", commento='" + commento + '\'' +
                '}';
    }
}
