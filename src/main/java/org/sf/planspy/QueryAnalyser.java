package org.sf.planspy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryAnalyser {
    private QueryInformation queryInformation;
    private String plan;

    public QueryAnalyser(QueryInformation queryInformation) {
        this.queryInformation = queryInformation;
    }

    public void run() {
        Connection connection = queryInformation.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet s = statement.executeQuery("EXPLAIN " + queryInformation.getStatement());

            if (s != null) {
                s.next();
                plan = s.getString(1);
            }
        } catch (SQLException e) {
            // swallow
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // swallow
                e.printStackTrace();
            }
        }
    }

    public boolean hasPlanBeenProduced() {
        return plan != null;
    }

    public String getPlan() {
        return plan;
    }
}
