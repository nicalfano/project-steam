package team2.develhope.project.steam.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsigliatiRTO {
    private String primaDescrizione = "Tre videogiochi consigliati dalla community in base ai tuoi generi preferiti:";
    private List<Videogioco> primaLista;
    private String secondaDescrizione = "Tre videogiochi consigliati in base alle tue recensioni:";
    private List<Videogioco> secondaLista;
}
