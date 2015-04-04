package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.proxy.GenericInvocationHandler;
import com.p6spy.engine.proxy.MethodNameMatcher;

import java.sql.Statement;


/**
 * Invocation handler for {@link Statement}
 */
class PlanSpyStatementInvocationHandler extends GenericInvocationHandler<Statement> {

    public PlanSpyStatementInvocationHandler(Statement underlying, final ConnectionInformation connectionInformation) {
        super(underlying);
        StatementInformation statementInformation = new StatementInformation(connectionInformation);

        PlanSpyStatementExecuteDelegate executeDelegate = new PlanSpyStatementExecuteDelegate(statementInformation);
        PlanSpyStatementExecuteBatchDelegate executeBatchDelegate = new PlanSpyStatementExecuteBatchDelegate(statementInformation);
        PlanSpyStatementAddBatchDelegate addBatchDelegate = new PlanSpyStatementAddBatchDelegate(statementInformation);


        addDelegate(
                new MethodNameMatcher("executeBatch"),
                executeBatchDelegate
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

    }

}
