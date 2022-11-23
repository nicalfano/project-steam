package team2.develhope.project.steam.entities;

public class ConnessioneDatabase {


    private final String url = new String("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11524855");
    private final String user = new String("sql11524855");
    private final String password = new String("ktaTYxUTb4");



    public ConnessioneDatabase(){}


    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }


}
