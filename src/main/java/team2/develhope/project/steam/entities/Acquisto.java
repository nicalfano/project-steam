package team2.develhope.project.steam.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_Acuqisto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_Utente")
    private Utente utente;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "id_Gioco")
    private Videogioco gioco;

    private LocalDate data_acquisto;  //Stringformat per trasformarla poi in stringa qualora si voglia stamparla

    private int in_libreria;



}
