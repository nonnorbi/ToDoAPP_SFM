package hu.kisno;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AppTest {

    private App appUnderTest;

    @BeforeEach
    void setUp() {
        appUnderTest = new App();
    }

    @Test
    void testStart() throws Exception {
        // Setup
        final Stage primaryStage = new Stage(StageStyle.DECORATED);

        // Run the test
        appUnderTest.start(primaryStage);

        // Verify the results
    }

    @Test
    void testStart_ThrowsIOException() {
        // Setup
        final Stage primaryStage = new Stage(StageStyle.DECORATED);

        // Run the test
        assertThrows(IOException.class, () -> appUnderTest.start(primaryStage));
    }

    @Test
    void testMain1() {
        // Setup

        // Run the test
        App.main(new String[]{"value"});

        // Verify the results
    }
}
