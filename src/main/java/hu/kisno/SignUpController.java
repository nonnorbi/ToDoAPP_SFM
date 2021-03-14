package hu.kisno;

import hu.kisno.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Label closeLabel;

    @FXML
    private Label registrationMassageLabel;

    @FXML
    private TextField sgnUpFisrtName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpUsername;

    @FXML
    private PasswordField signUpPasswird;

    @FXML
    private Button signUpButton;

    public void signUpButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = sgnUpFisrtName.getText();
        String lastname = signUpLastName.getText();
        String username = signUpUsername.getText();
        String password = signUpPasswird.getText();

        String insertFields = "INSERT INTO users(firstname, lastname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertRegister);

            registrationMassageLabel.setText("User has been registreted successfully!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }



    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
