package org.sf.planspy;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.SimpleJdbcEventListener;

import java.sql.SQLException;

/**
* Finally triggers the @{@link QueryAnalyser}.
*/
public class PlanSpyJdbcEventListener extends SimpleJdbcEventListener {

    static final PlanSpyJdbcEventListener INSTANCE = new PlanSpyJdbcEventListener();

    private PlanSpyJdbcEventListener() {
    }

    @Override
    public void onAfterAnyExecute(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        analyse(statementInformation);
    }

    @Override
    public void onAfterExecuteQuery(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
        analyse(statementInformation);
    }

    @Override
    public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
        analyse(statementInformation);
    }

    @Override
    public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        analyse(statementInformation);
    }

    @Override
    public void onAfterCallableStatementSet(CallableStatementInformation statementInformation, String parameterName, Object value, SQLException e) {
        analyse(statementInformation);
    }

    @Override
    public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        super.onAfterExecute(statementInformation, timeElapsedNanos, e);
    }

    private void analyse(StatementInformation statementInformation) {
        QueryAnalyser analyser = new QueryAnalyser(statementInformation);
        analyser.run();
        if (analyser.hasPlanBeenProduced()) {
            P6LogQuery.getLogger().logText(analyser.getPlan());
        } else {
            P6LogQuery.getLogger().logText("no plan for " + statementInformation.getSqlWithValues());
        }
    }
}
