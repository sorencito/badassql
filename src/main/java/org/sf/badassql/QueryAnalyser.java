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
            String sqlProviderClassname = BadasSQLOptions.getProperty(BadasSQLOptions.SQLPROVIDER);
            sqlProvider = (SQLProvider) this.getClass().getClassLoader().loadClass(sqlProviderClassname).newInstance();
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
        staticAnalysis();
        dynamicAnalysis();
    }

    private void dynamicAnalysis() {
        String planSql = sqlProvider.getSQLProvidingExecPlan(statementInformation);

        if (planSql == null)
            return;

        retrieveExecutionPlan(planSql);
        logExecutionPlan();
    }

    private void retrieveExecutionPlan(String planSql) {
        Statement statement = null;
        try {
            Connection connection = statementInformation.getConnectionInformation().getConnection();

            if (connection == null)
                return;

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

    private void logExecutionPlan() {
        if (hasPlanBeenProduced()) {
            P6LogQuery.getLogger().logText(getPlan());
        } else {
            P6LogQuery.getLogger().logText("no plan for " + statementInformation.getSqlWithValues());
        }
    }

    private void staticAnalysis() {
        if (new RegexBadasSQLMatcher().match(statementInformation.getSql()))
            throw new BadasSQLException("bad because matching regex " + BadasSQLOptions.getProperty(BadasSQLOptions.REGEX1) + "\n" + statementInformation.getSql());
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
