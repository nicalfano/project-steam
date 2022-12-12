package team2.develhope.project.steam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_Recensione;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_Acquisto")
    private Acquisto acquisto;
    @Column(nullable = false)
    private int voto;
    private String commento;

    public Recensione(Acquisto acquisto, int voto, String commento){
        this.acquisto = acquisto;
        this.voto = voto;
        this.commento = commento;
    }

    public Recensione(int voto, String commento){
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
