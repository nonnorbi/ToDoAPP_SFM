package hu.kisno;

import hu.kisno.animations.Shaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @FXML
    private Label errorSignUpMassageLabel;

    public void signUpButtonOnAction(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = sgnUpFisrtName.getText();
        String lastname = signUpLastName.getText();
        String username = signUpUsername.getText();
        String password = signUpPasswird.getText();

        /*  Statement statement  = connectDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()){
                String name = rs.getString("username");
                if(name.equals(signUpUsername)){
                    System.out.println("Not available username!");*/ // FIND method mysql

           String insertFields = "INSERT INTO users(firstname, lastname, username, password) VALUES ('";
           String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
           String insertRegister = insertFields + insertValues;
           try {
               Statement statement = connectDB.createStatement();
               statement.executeUpdate(insertRegister);

               registrationMassageLabel.setText("User has been registreted successfully!");

               wait(50);

               try {

                   signUpButton.getScene().getWindow().hide();
                   Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                   Stage registerStage = new Stage();
                   registerStage.initStyle(StageStyle.UNDECORATED);
                   registerStage.setScene(new Scene(root, 700, 400));
                   registerStage.showAndWait();

               } catch (IOException e) {
                   e.printStackTrace();
               }

           } catch (Exception e) {

               errorSignUpMassageLabel.setText("Username is not available!");
               System.out.println("Username is not available!");
               Shaker firstName = new Shaker(sgnUpFisrtName);
               Shaker lastName = new Shaker(signUpLastName);
               Shaker userName = new Shaker(signUpUsername);
               Shaker pwd = new Shaker(signUpPasswird);

               firstName.shake();
               lastName.shake();
               userName.shake();
               pwd.shake();

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
