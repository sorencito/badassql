package org.sf.planspy;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.GenericInvocationHandler;
import com.p6spy.engine.proxy.MethodNameMatcher;

import java.sql.CallableStatement;

/**
 * Invocation handler for {@link java.sql.PreparedStatement}
 */
class PlanSpyCallableStatementInvocationHandler extends GenericInvocationHandler<CallableStatement> {

    public PlanSpyCallableStatementInvocationHandler(CallableStatement underlying,
                                                     ConnectionInformation connectionInformation,
                                                     String query) {

        super(underlying);
        CallableStatementInformation callableStatementInformation = new CallableStatementInformation(connectionInformation);
        callableStatementInformation.setStatementQuery(query);

        PlanSpyPreparedStatementExecuteDelegate executeDelegate = new PlanSpyPreparedStatementExecuteDelegate(callableStatementInformation);
        PlanSpyPreparedStatementAddBatchDelegate addBatchDelegate = new PlanSpyPreparedStatementAddBatchDelegate(callableStatementInformation);
        PlanSpyCallableStatementSetParameterValueDelegate setParameterValueDelegate = new PlanSpyCallableStatementSetParameterValueDelegate(callableStatementInformation);

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
