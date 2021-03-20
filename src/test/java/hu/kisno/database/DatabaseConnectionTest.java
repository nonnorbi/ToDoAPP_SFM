package hu.kisno.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.mockito.Mockito.*;

class DatabaseConnectionTest {
    @Mock
    Connection databaseLink;
    @InjectMocks
    DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetConnection() {
        Connection result = databaseConnection.getConnection();
        Assertions.assertEquals(null, result);
    }
}
