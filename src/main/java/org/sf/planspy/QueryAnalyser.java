package org.sf.planspy;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.wrapper.ConnectionWrapper;
import org.sf.planspy.h2.H2SQLProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is where the query is analysed and finally, where the execution plan is retrieved from the database.
 */
class QueryAnalyser {

    // TODO: now this should be configurable, just making things work
    private final H2SQLProvider sqlProvider = new H2SQLProvider();
    private StatementInformation statementInformation;
    private String plan;

    QueryAnalyser(StatementInformation queryInformation) {
        this.statementInformation = queryInformation;
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
}
