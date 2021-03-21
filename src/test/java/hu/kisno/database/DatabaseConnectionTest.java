package hu.kisno.database;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DatabaseConnectionTest {

    private DatabaseConnection databaseConnectionUnderTest;

    @BeforeEach
    void setUp() {
        databaseConnectionUnderTest = new DatabaseConnection();
        Connection con = databaseConnectionUnderTest.getConnection();

    }

    @Test
    void testGetConnection() {
        // Setup
        setUp();

        // Run the test
        final Connection result = databaseConnectionUnderTest.getConnection();

        // Verify the results
    }
}
