package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;

public class PlanSpyConnectionRollbackDelegate implements Delegate {


    private final ConnectionInformation connectionInformation;

    public PlanSpyConnectionRollbackDelegate(final ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return method.invoke(underlying, args);
        } finally {
            P6LogQuery.logElapsed(connectionInformation.getConnectionId(), startTime, Category.ROLLBACK, "", "");
        }
    }
}
