package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.Delegate;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.Statement;

class PlanSpyConnectionCreateStatementDelegate implements Delegate {
    private final ConnectionInformation connectionInformation;

    public PlanSpyConnectionCreateStatementDelegate(final ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        Statement statement = (Statement) method.invoke(underlying, args);
        PlanSpyStatementInvocationHandler invocationHandler = new PlanSpyStatementInvocationHandler(statement, connectionInformation);
        return ProxyFactory.createProxy(statement, invocationHandler);
    }

    ConnectionInformation getConnectionInformation() {
        return connectionInformation;
    }
}
