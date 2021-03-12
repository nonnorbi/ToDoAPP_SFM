package hu.kisno;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMassageLabel;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void loginButtonOnAction(ActionEvent event){
        if(loginUsername.getText().isBlank() == false && loginPassword.getText().isBlank() == false) {
            validateLogin();
        }else{
            loginMassageLabel.setText("Please enter username and password!");
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + loginUsername.getText()
                + "' AND password = '" + loginPassword.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1) ==  1){
                    loginMassageLabel.setText("Congratulations!");
                }else{
                    loginMassageLabel.setText("Invalid login. Please try again. ");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }


}