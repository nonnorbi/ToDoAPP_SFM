package hu.kisno;

import hu.kisno.animations.Shaker;
import hu.kisno.database.DatabaseConnection;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.*;
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
    @FXML
    private Button loginSignUpButton;
    @FXML
    private AnchorPane rootAPane;

    int uid;

    //Sign Up screen
    public void loginSignUpButtonOnAction(ActionEvent event){

        try {

            //loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("signup.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 700, 400));
            registerStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    //Login validate
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(*) FROM users WHERE username = '" + loginUsername.getText()
                + "' AND password = '" + loginPassword.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + loginUsername.getText() + "'");
            while (rs.next()){
                uid = rs.getInt("userid");
            }
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1) ==  1){

                    try {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("addItem.fxml"));
                        loader.load();
                        Parent root = loader.getRoot();

                        try(PrintStream ps = new PrintStream("uID.txt") ){
                            ps.print(uid);
                        }catch ( FileNotFoundException e ){
                            e.printStackTrace();
                            e.getCause();
                        }

                        Stage addItemStage = new Stage();
                        addItemStage.initStyle(StageStyle.UNDECORATED);
                        addItemStage.setScene(new Scene(root, 900, 400));
                        addItemStage.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    loginMassageLabel.setText("Invalid login. Please try again. ");
                    Shaker userNameShaker = new Shaker(loginUsername);
                    Shaker passwordShaker = new Shaker(loginPassword);
                    userNameShaker.shake();
                    passwordShaker.shake();
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}