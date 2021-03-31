package hu.kisno;

import hu.kisno.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
    @FXML
    private Label errorLabel;
    @FXML
    private Label nameLabel;

    DatabaseConnection connectNow = new DatabaseConnection();
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
    public void readFile() throws IOException {

        FileReader inputReader = new FileReader("C:/Users/kisno/IdeaProjects/ToDoAPP_SFM/uID.txt"); //C:\Users\kisno\IdeaProjects\ToDoAPP_SFM\
        BufferedReader reader = new BufferedReader(inputReader);
        String line ;
        while ( (line = reader.readLine()) != null ){
            uID = Integer.parseInt(line.trim());
        }
        reader.close();
    }

    //Initialize the table
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con = connectNow.getConnection();
        LocalDate localDate =  LocalDate.now();
        String ldate = localDate.toString();
        int alert = 0;

        //Read uID
        try {
            readFile();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM users WHERE userid ='" + uID + "'");
            while (rs.next()){
                nameLabel.setText( rs.getString("lastname") + " " + rs.getString("firstname") + " (" + rs.getString("username")
                + ") 's todo list!" );
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid  = u.userid WHERE u.userid LIKE '" + uID + "'");
            while (rs.next()){
                oblist.add(new Task( rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                        rs.getString("task"), rs.getString("deadline") ));
                String deadline = rs.getString("deadline");
                if ( ldate.compareTo(deadline) > 0 ){
                    alert++;
                }
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }
        //Alert for expired deadline
        if ( alert > 0 ){

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.UNDECORATED);
            window.setWidth(300);
            window.setHeight(130);
            Label label = new Label();
            label.setText("You have " + alert + "task(s) that have expired!");
            Label label2 = new Label();
            label2.setText("Please delete it/them or set new deadline(s)");
            Button closeButton = new Button("OK");
            closeButton.setOnAction(e-> window.close());
            VBox layout = new VBox();
            layout.getChildren().addAll(label, label2, closeButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();

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

    //Add task
    @FXML
    public void addOnAction(MouseEvent event) {
        Connection con = connectNow.getConnection();

        if ( taskField.getText() == null || descriptionField.getText() == null || dateFld.getValue() == null ){
            errorLabel.setText("Please give all information!");
            System.out.println("Error!");
        }else{
            errorLabel.setText("");
        }


        System.out.println("Add item (from textfields): " + taskField.getText() + " " + descriptionField.getText() + " " + String.valueOf(dateFld.getValue()) + "\n" );

        String insertField = "INSERT INTO tasks(userid, description, task, deadline) VALUES('";
        String insertValues = uID + "','" + descriptionField.getText() + "','" + taskField.getText() + "','" + String.valueOf(dateFld.getValue()) + "')";
        String insertTask = insertField + insertValues;

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(insertTask);
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.task = '" + uID + "'");

            refresh();

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }
        taskField.setText("");
        descriptionField.setText("");
        dateFld.setValue(null);

    }

    //Delete task (row)
    @FXML
    public void deleteOnAction(MouseEvent event) {
        Connection con = connectNow.getConnection();

        task = table.getSelectionModel().getSelectedItem();

        System.out.println("Delete row: " + task.getTaskid() + " " + task.getUserid() + " | " + task.getTask() + ", " + task.getDescription() + ", " + task.getDeadline());
        System.out.println(uID + "\n");

        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM tasks WHERE taskid = '" + task.getUserid() + "'");
            ps.execute();
            refresh();

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

    //Edit task
    @FXML
    public void editableOnAction(MouseEvent event) {
        Connection con = connectNow.getConnection();

        task = table.getSelectionModel().getSelectedItem();

        System.out.println("taskid:" + task.getUserid() + ", userid:" + task.getTaskid() + " | " + task.getTask() + ", " + task.getDescription() + ", " + task.getDeadline());
        System.out.println(uID + "\n");

        try {
            PreparedStatement ps = con.prepareStatement("UPDATE tasks SET task = '" + task.getTask() + "', description = '" + task.getDescription()
                    + "', deadline = '" + task.getDeadline() + "' WHERE taskid = '" + task.getUserid() + "'");
            ps.execute();

            ResultSet rs =con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID + "'");
            System.out.println("kiolvasás" + "uid:" + uID + "taskid:" + task.getTaskid() );
            while (rs.next()){
                int uid = rs.getInt("userid");
                int tid = rs.getInt("taskid");
                String task = rs.getString("task");
                String description = rs.getString("description");
                String deadline = rs.getString("deadline");

                System.out.println("userid: " + uid + " taskid:" + tid + " | " +  task + ", " + description + ", " + deadline);
            }
            System.out.println("\n");


            refresh();

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

    //Search task
    @FXML
    public void searchOnAction(MouseEvent event) {
        Connection con = connectNow.getConnection();

        oblist.clear();

        if ( (dateFld.getValue() == null) && (taskField.getText().isBlank()) && (descriptionField.getText().isBlank()) ){ //Every field is blank

            errorLabel.setText("Please give at least deadline!");
            System.out.println("IF deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

        }else if ( !(dateFld.getValue() == null) && (taskField.getText().isBlank()) && (descriptionField.getText().isBlank()) ){ //Search by deadline

            errorLabel.setText("");
            System.out.println("ELSE IF deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.deadline = '" + String.valueOf(dateFld.getValue()) + "'" );
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }else if( !(dateFld.getValue() == null) && !(taskField.getText().isBlank()) && (descriptionField.getText().isBlank()) ){ //Search by deadline and task

            errorLabel.setText("");
            System.out.println("ELSE IF2 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.deadline = '" + String.valueOf(dateFld.getValue()) + "' AND t.task = '" + taskField.getText() + "'" );
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }else if ( !(dateFld.getValue() == null) && !(taskField.getText().isBlank()) && !(descriptionField.getText().isBlank()) ){ //Every field is filld

            errorLabel.setText("");
            System.out.println("ELSE IF2 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.deadline = '" + String.valueOf(dateFld.getValue()) + "' AND t.task = '"
                        + taskField.getText() + "' AND t.description = '" + descriptionField.getText() + "'" );
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }else if ( (dateFld.getValue() == null) && !(taskField.getText().isBlank()) && !(descriptionField.getText().isBlank()) ){ //Search by task and description

            errorLabel.setText("");
            System.out.println("ELSE IF3 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.task = '" + taskField.getText() + "' AND t.description = '" + descriptionField.getText() + "'");
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }else if ( !(dateFld.getValue() == null) && (taskField.getText().isBlank()) && !(descriptionField.getText().isBlank()) ){ //Search by deadline and description

            errorLabel.setText("");
            System.out.println("ELSE IF4 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.deadline = '" + String.valueOf(dateFld.getValue()) + "' AND t.description = '" + descriptionField.getText() + "'");
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }
        }else if ( (dateFld.getValue() == null) && !(taskField.getText().isBlank()) && (descriptionField.getText().isBlank()) ){ //Search by task

            errorLabel.setText("");
            System.out.println("ELSE IF4 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.task = '" + taskField.getText() + "'");
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }else if ( (dateFld.getValue() == null) && (taskField.getText().isBlank()) && !(descriptionField.getText().isBlank()) ){ //Search by description

            errorLabel.setText("");
            System.out.println("ELSE IF5 deadline: " + dateFld.getValue() + " task: " + taskField.getText() + " descript: " + descriptionField.getText());

            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid = u.userid WHERE u.userid LIKE '" + uID +
                        "' AND t.description = '" + descriptionField.getText() + "'");
                while (rs.next()){
                    oblist.add(new Task(rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                            rs.getString("task"), rs.getString("deadline" )) );
                }
                col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
                col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
                col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

                table.setItems(oblist);

            } catch ( SQLException throwables ) {
                throwables.printStackTrace();
                throwables.getCause();
            }

        }


    }

    //Table refresh
    public void refresh(){
        Connection con = connectNow.getConnection();

        oblist.clear();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT t.* FROM users u INNER JOIN tasks t ON t.userid  = u.userid WHERE u.userid LIKE '" + uID + "'");
            while (rs.next()){
                oblist.add(new Task( rs.getInt("userid"), rs.getInt("taskid"), rs.getString("description"),
                        rs.getString("task"), rs.getString("deadline") ) );
            }

            col_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
            col_task.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
            col_deadline.setCellValueFactory(new PropertyValueFactory<Task, String>("deadline"));

            table.setItems(oblist);

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }
    }
}