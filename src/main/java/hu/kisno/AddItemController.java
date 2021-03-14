package hu.kisno;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    @FXML
    private URL url;
    @FXML
    private ResourceBundle resourceBundle;
    @FXML
    private Label notTaskLabel;
    @FXML
    private ImageView addItemImg;

    Image addImg = new Image(getClass().getResourceAsStream("icons/add_icon.png"));
    Image closeImg = new Image(getClass().getResourceAsStream("icons/close_icon.png"));

    public void addItem(){}

    public void close(MouseEvent event) {
        System.exit(0);
    }

}