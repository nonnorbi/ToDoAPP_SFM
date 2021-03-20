package hu.kisno;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class AddItemControllerTest {
    @Mock
    TableView<Task> table;
    @Mock
    TableColumn<Task, String> col_task;
    @Mock
    TableColumn<Task, String> col_description;
    @Mock
    TableColumn<Task, String> col_deadline;
    @Mock
    TextField taskField;
    @Mock
    TextField descriptionField;
    @Mock
    DatePicker dateFld;
    @Mock
    ObservableList<Task> oblist;
    @Mock
    Task task;
    @InjectMocks
    AddItemController addItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCloseOnAction() {
        addItemController.closeOnAction(null);
    }

    @Test
    void testReadFile() {
        try {
            addItemController.readFile();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddOnAction() {
        addItemController.addOnAction(null);
    }

    @Test
    void testDeleteOnAction() {
        when(task.getTaskid()).thenReturn(0);

        addItemController.deleteOnAction(null);
    }

    @Test
    void testRefresh() {
        addItemController.refresh();
    }

    @Test
    void testInitialize() {
        addItemController.initialize(null, null);
    }
}
