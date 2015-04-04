package org.sf.planspy.h2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sf.planspy.P6SpyConstants;
import org.sf.planspy.PlanSpyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class H2Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private Connection conn;

    @Before
    public void openConnectionAndRedirectStreams() throws ClassNotFoundException, SQLException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Class.forName(P6SpyConstants.P6SPY_JDBC_DRIVER);
        conn = DriverManager.getConnection("jdbc:p6spy:h2:mem:", "sa", "");

    }

    @After
    public void closeConnectionAndCleanUpStreams() throws SQLException {
        System.setOut(null);
        System.setErr(null);

        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testH2Connection() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("select 1");
        stmt.close();

        assertThat(outContent.toString(), containsString(PlanSpyFactory.activationMsg));
    }
}