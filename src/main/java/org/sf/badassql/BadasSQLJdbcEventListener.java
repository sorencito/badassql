package org.sf.badassql;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.SimpleJdbcEventListener;

import java.sql.SQLException;

/**
* Finally triggers the @{@link QueryAnalyser}.
*/
public class BadasSQLJdbcEventListener extends SimpleJdbcEventListener {

    static final BadasSQLJdbcEventListener INSTANCE = new BadasSQLJdbcEventListener();

    private BadasSQLJdbcEventListener() {
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
    }
}
