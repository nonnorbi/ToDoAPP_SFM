module hu.kisno {
    requires javafx.base;
    requires java.datatransfer;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires fontawesomefx;
    requires javafx.graphicsEmpty;

    opens hu.kisno to javafx.fxml;
    exports hu.kisno;
}