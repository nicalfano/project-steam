package team2.develhope.project.steam.entities;

public class Videogioco {

    private int idVideogioco;
    private String titolo;
    private double prezzo;
    private String genere;
    private String genere2;
    private int pegi;
    private String producer;
    private String descrizione;
    private boolean online;
    private boolean singlePlayer;
    private boolean coop;
    private boolean pVp;

    public int getIdVideogioco() {
        return idVideogioco;
    }

    public String getTitolo() {
        return titolo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getGenere() {
        return genere;
    }

    public String getGenere2() {
        return genere2;
    }

    public int getPegi() {
        return pegi;
    }

    public String getProducer() {
        return producer;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public boolean isCoop() {
        return coop;
    }

    public boolean ispVp() {
        return pVp;
    }
}
