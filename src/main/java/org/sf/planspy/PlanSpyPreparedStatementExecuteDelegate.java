package org.sf.planspy;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.proxy.Delegate;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.ResultSet;

class PlanSpyPreparedStatementExecuteDelegate implements Delegate {
    private final PreparedStatementInformation preparedStatementInformation;

    public PlanSpyPreparedStatementExecuteDelegate(final PreparedStatementInformation preparedStatementInformation) {
        this.preparedStatementInformation = preparedStatementInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            Object result = method.invoke(underlying, args);

            if (result != null && result instanceof ResultSet) {
                PlanSpyResultSetInvocationHandler resultSetInvocationHandler = new PlanSpyResultSetInvocationHandler((ResultSet) result, preparedStatementInformation);
                result = ProxyFactory.createProxy((ResultSet) result, resultSetInvocationHandler);
            }
            return result;

        } finally {
//            P6LogQuery.logElapsed(preparedStatementInformation.getConnectionId(), startTime, Category.STATEMENT, preparedStatementInformation);
        }
    }
}
