package team2.develhope.project.steam.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table

public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_Acquisto;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_Utente")
    private Utente utente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "id_videogioco")
    private Videogioco gioco;


    //private Timestamp data_acquisto;  //Stringformat per trasformarla poi in stringa qualora si voglia stamparla

    @Column
    private boolean in_libreria;

public Acquisto (Utente utente,Videogioco videogioco, Timestamp data_acquisto, boolean in_libreria){
    this.utente = utente;
    this.gioco = videogioco;
    //this.data_acquisto = data_acquisto;
    this.in_libreria = in_libreria;
}

    public void setIn_libreria(boolean in_libreria) {
        this.in_libreria = in_libreria;
    }
}
