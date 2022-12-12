package team2.develhope.project.steam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utente {

    private String nome;
    private String cognome;
    private int età;
    private String email;
    private String username;
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUtente;
    private double saldo = 0;
    private boolean loginStatus = false;



    public Utente(String nome, String cognome, int età, String email, String username, String pw, double saldo, boolean loginStatus){
        this.nome = nome;
        this.cognome = cognome;
        this.età = età;
        this.email = email;
        this.username = username;
        this.password = pw;
        this.saldo = saldo;
        this.loginStatus = loginStatus;
    }


    public boolean checkUsername(String username) throws SQLException {
        while (true) {
            ConnessioneDatabase connessione = new ConnessioneDatabase();
            Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
            Statement statement = connection.createStatement();
            ArrayList<String> risultatoRicerca = new ArrayList<>();

            String q = "SELECT Username FROM Utente WHERE Username = \"" + username + "\";";

            ResultSet usernameUtenti = statement.executeQuery(q);

            while (usernameUtenti.next()) {
                risultatoRicerca.add(usernameUtenti.getString("Username"));
            }
            if (risultatoRicerca.contains(username)) {
                System.out.println("Not valid username!");
                return true;
            } else {
                System.out.println("Valid username!");
                return false;
            }
        }
    }




    public void registraAccount(Utente utente) throws SQLException {

        Scanner input = new Scanner(System.in);

        System.out.print("Nome: ");
        this.nome = input.next();

        System.out.print("Cognome: ");
        this.cognome = input.next();

        System.out.print("Età: ");
        this.età = Integer.parseInt(input.next());

        System.out.print("email: ");
        this.email = input.next();

        System.out.print("Imposta password: ");
        this.password = input.next();

        do{
        System.out.print("Ripeti password: ");
        }while (!(input.next().equals(password)));

        do {
            System.out.print("username: ");
            this.username = input.next();
            checkUsername(username);
        } while (checkUsername(username) == true);

        System.out.print("Vuoi caricare un saldo? (Y/N): ");
        String answer = input.next();
        switch (answer){
            case "Y":
                System.out.println("Quanto?: ");
                saldo = Double.parseDouble(input.next());
                System.out.println("Caricati " + saldo + " $");

            case "N":
                saldo = 0;
        }


        utente = new Utente();

        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Utente (Nome, Cognome, Età, Username, Email, Password, Saldo) VALUES(?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, utente.getNome());
        statement.setString(2, utente.getCognome());
        statement.setInt(3, utente.getEtà());
        statement.setString(4, utente.getUsername());
        statement.setString(5, utente.getEmail());
        statement.setString(6, utente.getPassword());
        statement.setDouble(7, utente.getSaldo());

        statement.executeLargeUpdate();

        System.out.println("Benvenuto " + utente.getNome() + " " + utente.getCognome() + "!");
        System.out.println("La tua registrazione è andata a buon fine!");
    }

    public void ricercaTitolo(String titolo) throws Exception {
        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();
        String q =
                "SELECT Titolo FROM Videogioco WHERE Titolo = \"" + titolo + "\";";

        ResultSet titoliVideogiochi = statement.executeQuery(q);
        ArrayList<String> risultatoRicerca = new ArrayList<>();
        while (titoliVideogiochi.next()) {
            risultatoRicerca.add(titoliVideogiochi.getString("Titolo"));
        }
        if (risultatoRicerca.contains(titolo)) {
            System.out.println("il titolo selezionato è disponibile");
        } else {
            System.out.println("il titolo selezionato non è disponibile");
        }

    }


    public void stampaTitoli() throws Exception {
        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();
        String q = """
                SELECT Titolo
                FROM Videogioco;
                """;
        ResultSet titoliVideogiochi = statement.executeQuery(q);
        while (titoliVideogiochi.next()) {
            System.out.println(titoliVideogiochi.getString("Titolo"));
        }
    }

    public void esploraLibreriaGlobale() throws Exception {
        while (true) {
            System.out.println("ricerchi un titolo in particolare?");
            Scanner input = new Scanner(System.in);
            String risposta = input.next();
            if (risposta.equals("si")) {
                System.out.println("Scrivi il titolo del videogioco che stai cercando(per adesso la ricerca è case sensitive)");
                Scanner input1 = new Scanner(System.in);
                String risposta1 = input1.nextLine();
                ricercaTitolo(risposta1);
                break;
            } else if (risposta.equals("no")) {
                stampaTitoli();
                break;
            } else {
                System.out.println("dovresti rispondere \"si\" oppure \"no\"");
            }
        }
    }
//il metodo recensisci videogioco è da sistemare
    public Recensione recensisciVideogioco(Acquisto acquistoVideogioco) {
        if (acquistoVideogioco.getUtente().idUtente == this.idUtente) {
            System.out.println("inserisci un voto da 1 a 5");
            Scanner input = new Scanner(System.in);
            int voto = input.nextInt();
            if (voto >= 1 && voto <= 5) {
                System.out.println("inserisci il commento tutto in una riga");
                Scanner input1 = new Scanner(System.in);
                String commento = input1.nextLine();
                Recensione recensione = new Recensione(acquistoVideogioco,voto, commento);
                return recensione;
            } else {
                System.out.println("il voto inserito non è corretto");
            }

        }

    return new Recensione();
    }


    public void esploraLibreriaPersonale(Utente utente) throws SQLException {
        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM Videogioco INNER JOIN Acquisto ON Videogioco.IdVideoGioco = Acquisto.id_Gioco WHERE Acquisto.id_Utente = " + utente.getIdUtente();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            System.out.print( result.getString(2) + "  -  " + result.getString(3) + "  -  " + result.getString(4));
            System.out.println();
        }
    }

    public void visualizzaRecensioniGioco(Videogioco videogioco) throws SQLException {
        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM Recensione INNER JOIN Videogioco ON Recensione.id_Gioco = Videogioco.IdVideoGioco WHERE Videogioco.IdVideoGioco = " + videogioco.getIdVideogioco();

        ResultSet result = statement.executeQuery(query);

        System.out.println(videogioco.getTitolo());
        while (result.next()) {
            System.out.print( result.getString(4) + "  -  " + result.getString(5));
            System.out.println();
        }
    }

    public void acquista(String titolo) throws SQLException {

        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();

        ResultSet result2 = statement.executeQuery("SELECT * FROM Videogioco WHERE Videogioco.Titolo = '" + titolo + "'");

        double prezzo = 0;
        while (result2.next()) {
            prezzo = result2.getDouble("Prezzo");
        }

        if (this.saldo >= prezzo) {
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO Acquisto ( id_Utente, id_Gioco, in_libreria ) VALUES (" + this.getIdUtente() + ",(SELECT IdVideoGioco FROM Videogioco WHERE Videogioco.Titolo = '" + titolo + "'),1);");
            statement1.executeUpdate();

            this.saldo -= prezzo;
        } else {
            System.out.println("Saldo insufficiente ! ");
        }

        double saldo_aggiornato = this.getSaldo();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE Utente SET Saldo = " + saldo_aggiornato + " WHERE Utente.id_Utente = " + this.idUtente );
        statement2.executeUpdate();
    }

    public void eliminaGiocoDallaLibreria( String titolo ) throws Exception{

        ConnessioneDatabase connessione = new ConnessioneDatabase();
        Connection connection = DriverManager.getConnection(connessione.getUrl(), connessione.getUser(), connessione.getPassword());
        Statement statement = connection.createStatement();

        ResultSet result2 = statement.executeQuery("SELECT * FROM Videogioco WHERE Videogioco.Titolo = '" + titolo + "'");
        int id_videogioco = 0;
        while (result2.next()) {
            id_videogioco = result2.getInt("IdVideoGioco");
            System.out.println(id_videogioco);
        }

        PreparedStatement statement2 = connection.prepareStatement("UPDATE Acquisto SET in_libreria = 0 WHERE Acquisto.id_Utente = " + this.getIdUtente() + " AND Acquisto.id_Gioco = " + id_videogioco );
        statement2.executeUpdate();

    }




}