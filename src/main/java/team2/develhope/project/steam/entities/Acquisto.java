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
    private long idAcuqisto;

    @Column(nullable = false)
    private long idUtente;

    @Column(nullable = false)
    private int idGioco;
    @Column(nullable = false)
    private LocalDate data_acquisto;  //Stringformat per trasformarla poi in stringa qualora si voglia stamparla




}
