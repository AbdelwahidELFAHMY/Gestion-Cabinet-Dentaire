package com.example.cabinet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBase {

    public static Connection connect_DB() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cabinet_dentaire", "root", "");
            if (connect != null) {
                System.out.println("Connexion à la base de données établie !");
            } else {
                System.out.println("La connexion est null !");
            }
            return connect;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la base de données : ");
            return null;
        }
        // }
        // catch (Exception e){ e.printStackTrace();System.out.println("mochkil fdiiik fonction coneectDB().");}

    }


}
