module hu.kisno {
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.kisno to javafx.fxml;
    exports hu.kisno;
}