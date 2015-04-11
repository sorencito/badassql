package org.sf.planspy;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.proxy.Delegate;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;

class PlanSpyStatementExecuteDelegate implements Delegate {
    private final StatementInformation statementInformation;

    public PlanSpyStatementExecuteDelegate(final StatementInformation statementInformation) {
        this.statementInformation = statementInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        statementInformation.setStatementQuery((String) args[0]);
        long startTime = System.currentTimeMillis();

        try {
            Object result = method.invoke(underlying, args);
            if (result != null && result instanceof ResultSet) {
                PlanSpyResultSetInvocationHandler resultSetInvocationHandler = new PlanSpyResultSetInvocationHandler((ResultSet) result, statementInformation);
                result = ProxyFactory.createProxy((ResultSet) result, resultSetInvocationHandler);
            }
            return result;
        } finally {
            QueryAnalyser analyser = new QueryAnalyser(new QueryInformation(((Statement) underlying).getConnection(), (String) args[0]));
            analyser.run();

            if (analyser.hasPlanBeenProduced()) {
                P6LogQuery.getLogger().logText(analyser.getPlan());
            }
        }
    }
}
