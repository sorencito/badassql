package org.sf.badassql;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.StatementInformation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is where the query is analysed and finally, where the execution plan is retrieved from the database.
 */
class QueryAnalyser {

    private SQLProvider sqlProvider;

    private StatementInformation statementInformation;
    private String plan;

    QueryAnalyser(StatementInformation queryInformation) {
        this.statementInformation = queryInformation;
        try {
            sqlProvider = (SQLProvider) this.getClass().getClassLoader().loadClass(BadasSQLOptions.sqlProvider).newInstance();
        } catch (InstantiationException e) {
            logErrorSQLProvider(e);
        } catch (IllegalAccessException e) {
            logErrorSQLProvider(e);
        } catch (ClassNotFoundException e) {
            logErrorSQLProvider(e);
        }
    }

    private void logErrorSQLProvider(Exception e) {
        P6LogQuery.error("couldn't load SQL provider: " + sqlProvider + ", " + e.toString());
    }

    void run() {
        String planSql = sqlProvider.getSQLProvidingExecPlan(statementInformation);
        if (planSql == null) return;

        Statement statement = null;
        try {
            Connection connection = statementInformation.getConnectionInformation().getConnection();


            statement = connection.createStatement();
            ResultSet s = statement.executeQuery(planSql);

            if (s != null) {
                s.next();
                this.plan = s.getString(1);
            }
        } catch (SQLException e) {
            P6LogQuery.error("couldn't retrieve plan with query: " + planSql);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                P6LogQuery.error("LEAKING, couldn't close statement for query: " + planSql);
            }
        }
    }

    boolean hasPlanBeenProduced() {
        return plan != null;
    }

    String getPlan() {
        return plan;
    }

    String getSQLProviderClass() {
        if (sqlProvider != null)
            return sqlProvider.getClass().getCanonicalName();
        else return null;
    }
}
