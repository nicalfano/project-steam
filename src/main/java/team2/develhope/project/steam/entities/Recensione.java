package team2.develhope.project.steam.entities;

public class Recensione {

    private int idVideogioco;
    private int idUtente;
        private int voto;
    private String commento;

    public Recensione(int idVideogioco, int idUtente, int voto, String commento){
        this.idVideogioco = idVideogioco;
        this.idUtente = idUtente;
        this.voto = voto;
        this.commento = commento;
    }

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
