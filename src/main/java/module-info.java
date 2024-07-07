module com.example.cabinet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires java.xml.crypto;
    requires itextpdf;


    opens com.example.cabinet to javafx.fxml;
    exports com.example.cabinet;
}