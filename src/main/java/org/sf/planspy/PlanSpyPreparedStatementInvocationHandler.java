package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.proxy.GenericInvocationHandler;
import com.p6spy.engine.proxy.MethodNameMatcher;

import java.sql.PreparedStatement;

/**
 * Invocation handler for {@link java.sql.PreparedStatement}
 */
class PlanSpyPreparedStatementInvocationHandler extends GenericInvocationHandler<PreparedStatement> {

    public PlanSpyPreparedStatementInvocationHandler(PreparedStatement underlying,
                                                     ConnectionInformation connectionInformation,
                                                     String query) {

        super(underlying);
        PreparedStatementInformation preparedStatementInformation = new PreparedStatementInformation(connectionInformation);
        preparedStatementInformation.setStatementQuery(query);

        PlanSpyPreparedStatementExecuteDelegate executeDelegate = new PlanSpyPreparedStatementExecuteDelegate(preparedStatementInformation);
        PlanSpyPreparedStatementAddBatchDelegate addBatchDelegate = new PlanSpyPreparedStatementAddBatchDelegate(preparedStatementInformation);
        PlanSpyPreparedStatementSetParameterValueDelegate setParameterValueDelegate = new PlanSpyPreparedStatementSetParameterValueDelegate(preparedStatementInformation);

        addDelegate(
                new MethodNameMatcher("executeBatch"),
                executeDelegate
        );
        addDelegate(
                new MethodNameMatcher("addBatch"),
                addBatchDelegate
        );
        addDelegate(
                new MethodNameMatcher("execute"),
                executeDelegate
        );
        addDelegate(
                new MethodNameMatcher("executeQuery"),
                executeDelegate
        );
        addDelegate(
                new MethodNameMatcher("executeUpdate"),
                executeDelegate
        );
        addDelegate(
                new MethodNameMatcher("set*"),
                setParameterValueDelegate
        );


    }

}
