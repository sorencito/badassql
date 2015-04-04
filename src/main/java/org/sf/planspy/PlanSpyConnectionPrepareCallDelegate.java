package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.CallableStatement;

public class PlanSpyConnectionPrepareCallDelegate extends PlanSpyConnectionCreateStatementDelegate {

    public PlanSpyConnectionPrepareCallDelegate(final ConnectionInformation connectionInformation) {
        super(connectionInformation);
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        CallableStatement statement = (CallableStatement) method.invoke(underlying, args);
        String query = (String) args[0];
        PlanSpyCallableStatementInvocationHandler invocationHandler = new PlanSpyCallableStatementInvocationHandler(statement,
                getConnectionInformation(), query);
        return ProxyFactory.createProxy(statement, invocationHandler);
    }

}
