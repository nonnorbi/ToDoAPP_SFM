module hu.kisno {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens hu.kisno to javafx.fxml;
    exports hu.kisno;
}