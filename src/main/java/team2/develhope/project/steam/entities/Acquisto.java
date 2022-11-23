package team2.develhope.project.steam.entities;

import java.time.LocalDate;

public class Acquisto {

    private int idAcquisto;
    private int idUtente;
    private int idGioco;
    private LocalDate data_acquisto;  //Stringformat per trasformarla poi in stringa qualora si voglia stamparla

    public Acquisto(int idAcquisto,int idUtente, int idGioco, LocalDate data_acquisto){
        this.idAcquisto = idAcquisto;
        this.idUtente = idUtente;
        this.idGioco = idGioco;
        this.data_acquisto = data_acquisto;
    }

    public int getIdGioco() {
        return idGioco;
    }

    public int getIdAcquisto() {
        return idAcquisto;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public LocalDate getData_acquisto() {
        return data_acquisto;
    }


}
