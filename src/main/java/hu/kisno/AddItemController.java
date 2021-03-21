package hu.kisno;

import hu.kisno.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.util.ResourceBundle;


public class AddItemController implements Initializable {

    @FXML
    private TableView<Task> table;
    @FXML
    private TableColumn<Task, String> col_task;
    @FXML
    private TableColumn<Task, String> col_description;
    @FXML
    private TableColumn<Task, String> col_deadline;
    @FXML
    private TextField taskField;
    @FXML
    private TextField descriptionField;
    @FXML
    private DatePicker dateFld;

    ObservableList<Task> oblist = FXCollections.observableArrayList();
    int uID;
    Task task = null;

    //Close window and delete uID from file
    @FXML
    public void closeOnAction(MouseEvent event){
        /*try(PrintStream ps = new PrintStream("C:/Users/kisno/IdeaProjects/ToDoAPP_SFM/uID.txt")) {
            ps.println("");
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }*/
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //uID read from file
    /*public void readFile() {
        try( Scanner file = new Scanner(new File("C:\\Users\\kisno\\IdeaProjects\\ToDoAPP_SFM\\src\\main\\resources\\uID.txt")) ){
            while(file.hasNext()){
                uID = file.next();
                System.out.println(uID + " additem controller");
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
            e.getCause();
        }
    }*/
    public void readFile() throws IOException {

        FileReader inputReader = new FileReader("C:/Users/kisno/IdeaProjects/ToDoAPP_SFM/uID.txt"); //C:\Users\kisno\IdeaProjects\ToDoAPP_SFM\
        BufferedReader reader = new BufferedReader(inputReader);
        String line = null;
        while ( (line = reader.readLine()) != null ){
            //System.out.println(line);
            uID = Integer.parseInt(line.trim());
            //System.out.println(uID);
        }
        reader.close();
    }

    //Add task
    @FXML
    public void addOnAction(MouseEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        String task = taskField.getText();
        String descript = descriptionField.getText();
        String deadline = String.valueOf(dateFld.getValue());

        String insertField = "INSERT INTO tasks(userid, description, task, deadline) VALUES('";
        String insertValues = uID + "','" + descript + "','" + task + "','" + deadline + "')";
        String insertTask = insertField + insertValues;

        try{

            Statement stmt = con.createStatement();
            stmt.executeUpdate(insertTask);
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.task = '" + uID + "'");

            refresh();

        }catch ( SQLException throwables ){
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    //Delete Task
    public void deleteOnAction(MouseEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        task = table.getSelectionModel().getSelectedItem();
        try ( PreparedStatement pstmt = con.prepareStatement("DELETE FROM tasks WHERE taskid  = '" + task.getTaskid() + "'") ) {
            pstmt.execute();
            refresh();
        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    //Edit Task
    @FXML
    private void editableOnAction(MouseEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        task = table.getSelectionModel().getSelectedItem();
        int  id = task.getTaskid();
        String ts = task.getTask();
        String di = task.getDescription();
        String de = task.getDeadline();
        System.out.println(id + ", " + ts + ", " + di + ", " + de );

        try {
            PreparedStatement ps = con.prepareStatement( "UPDATE tasks SET task = '" + task.getTask() + "', description = '" + task.getDescription()
                    + "', deadline = '" + task.getDeadline() + "' WHERE taskid = '" + task.getTaskid() + "'" );
            ps.execute();

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }
        refresh();

    }

    //Refresh table
    public void refresh(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        oblist.clear();

        try {

            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid  = u.userid WHERE u.userid LIKE '" + uID + "'");
            while (rs.next()){
                oblist.add(new Task( rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                        rs.getString("task"), rs.getString("deadline") ));
            }

            col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
            col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
            col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

            table.setItems(oblist);


        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }
    }

    //Initialize the table
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        try {
            readFile();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid  = u.userid WHERE u.userid LIKE '" + uID + "'");
            while (rs.next()){
                oblist.add(new Task( rs.getInt("taskid"), rs.getInt("userid"), rs.getString("description"),
                        rs.getString("task"), rs.getString("deadline") ));
            }

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }

        col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

        col_task.setCellFactory(TextFieldTableCell.forTableColumn());
        col_task.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTask(e.getNewValue());
        });

        col_description.setCellFactory(TextFieldTableCell.forTableColumn());
        col_description.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDescription(e.getNewValue());
        });

        col_deadline.setCellFactory(TextFieldTableCell.forTableColumn());
        col_deadline.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDeadline(e.getNewValue());
        });

        table.setEditable(true);


        table.setItems(oblist);



    }

}