package org.sf.planspy;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;

class PlanSpyPreparedStatementAddBatchDelegate implements Delegate {
    private final PreparedStatementInformation preparedStatementInformation;

    public PlanSpyPreparedStatementAddBatchDelegate(final PreparedStatementInformation preparedStatementInformation) {
        this.preparedStatementInformation = preparedStatementInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return method.invoke(underlying, args);
        } finally {
//            P6LogQuery.logElapsed(preparedStatementInformation.getConnectionId(), startTime, Category.BATCH,
//                    preparedStatementInformation);
        }
    }
}
