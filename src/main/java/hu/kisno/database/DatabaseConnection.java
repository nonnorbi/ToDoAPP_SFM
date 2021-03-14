package hu.kisno.database;

import java.sql.DriverManager;
import java.sql.Connection;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "todo";
        String databaseUser = "root";
        String databasePassword = "Kn_19950801";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;

    }

}
