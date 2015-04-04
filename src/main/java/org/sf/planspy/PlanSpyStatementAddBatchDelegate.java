package org.sf.planspy;

import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;

class PlanSpyStatementAddBatchDelegate implements Delegate {

    private final StatementInformation statementInformation;

    public PlanSpyStatementAddBatchDelegate(final StatementInformation statementInformation) {
        this.statementInformation = statementInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        statementInformation.setStatementQuery((String) args[0]);
        long startTime = System.currentTimeMillis();

        try {
            return method.invoke(underlying, args);
        } finally {
//            P6LogQuery.logElapsed(statementInformation.getConnectionId(), startTime, Category.BATCH, statementInformation);
        }
    }
}
