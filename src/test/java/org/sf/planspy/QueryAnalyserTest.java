package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryAnalyserTest {

    private Connection mockedConnection;

    @Before
    public void setup() throws SQLException {
        mockedConnection = mock(Connection.class);
        Statement mockedStatement = mock(Statement.class);
        ResultSet mockedResultSet = mock(ResultSet.class);
        when(mockedConnection.createStatement()).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery(any())).thenReturn(mockedResultSet);
    }

    @Test
    public void test_empty_query_information_doesnt_produce_plan() {
        QueryAnalyser analyser = new QueryAnalyser(new StatementInformation(ConnectionInformation.fromDriver(null, mockedConnection, 0)));
        analyser.run();

        assertFalse(analyser.hasPlanBeenProduced());
    }

}