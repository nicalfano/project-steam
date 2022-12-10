package team2.develhope.project.steam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Videogioco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idVideogioco;
    @Column(nullable = false,unique = true)
    private String titolo;
    @Column(nullable = false)
    private double prezzo;
    @Column(nullable = false)
    private String genere;
    private String genere2;
    @Column(nullable = false)
    private int pegi;
    @Column(nullable = false)
    private String producer;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private boolean online;
    @Column(nullable = false)
    private boolean singlePlayer;
    @Column(nullable = false)
    private boolean coop;
    @Column(nullable = false)
    private boolean pVp;



}
