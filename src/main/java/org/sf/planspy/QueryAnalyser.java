package org.sf.planspy;

import org.sf.planspy.h2.H2SQLProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryAnalyser implements SQLProvider {

    private final H2SQLProvider h2SQLProvider = new H2SQLProvider();
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
            ResultSet s = statement.executeQuery(h2SQLProvider.getSQLProvidingExecPlan(queryInformation));

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

    @Override
    public String getSQLProvidingExecPlan(QueryInformation queryInformation) {
        return h2SQLProvider.getSQLProvidingExecPlan(queryInformation);
    }

    public boolean hasPlanBeenProduced() {
        return plan != null;
    }

    public String getPlan() {
        return plan;
    }
}
