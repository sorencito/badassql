package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;

/**
 */
public class PlanSpyConnectionPrepareStatementDelegate extends PlanSpyConnectionCreateStatementDelegate {

    public PlanSpyConnectionPrepareStatementDelegate(final ConnectionInformation connectionInformation) {
        super(connectionInformation);
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        PreparedStatement statement = (PreparedStatement) method.invoke(underlying, args);
        String query = (String) args[0];
        PlanSpyPreparedStatementInvocationHandler invocationHandler = new PlanSpyPreparedStatementInvocationHandler(statement,
                getConnectionInformation(), query);
        return ProxyFactory.createProxy(statement, invocationHandler);
    }

}
