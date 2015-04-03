package org.sf.planspy.h2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sf.planspy.P6SpyConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Test {

    private Connection conn;

    @Before
    public void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(P6SpyConstants.P6SPY_JDBC_DRIVER);
        conn = DriverManager.getConnection(H2Constants.IN_MEMORY_DB_URL, "sa", "");
    }

    @After
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testH2Connection() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("select 1");
        stmt.close();
    }
}