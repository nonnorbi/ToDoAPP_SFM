package hu.kisno;

import hu.kisno.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;


public class AddItemController implements Initializable {


    @FXML
    private TableView<Task> table;

    @FXML
    private TableColumn<Task, String> col_task;

    @FXML
    private TableColumn<Task, String> col_description;

    @FXML
    private TableColumn<Task, String> col_deadline;

    ObservableList<Task> oblist = FXCollections.observableArrayList();
    int uID;

    //Close window and delete uID from file
    @FXML
    public void closeOnAction(MouseEvent event){
        try(PrintStream ps = new PrintStream("uID.txt")) {
            ps.println("");
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //uID read from file
    public void read(){
        try(Scanner file = new Scanner(new File("uID.txt"))) {
            while (file.hasNextInt()){
                uID = file.nextInt();
                System.out.println(uID);
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addOnAction(){}
    public void deleteOnAction(){}

    //Initialize the table
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid  = u.userid WHERE u.userid LIKE '" + 69 + "'");
            while (rs.next()){
                oblist.add(new Task( rs.getInt("taskid"), rs.getInt("userid"), rs.getString("description"),
                        rs.getString("task"), rs.getString("deadline") ));
                String task = rs.getString("task");
                String descript = rs.getString("description");
                String deadline = rs.getString("deadline");
                System.out.println(task + " " + descript + " " + deadline);
            }

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }

        col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

        table.setItems(oblist);

    }
}