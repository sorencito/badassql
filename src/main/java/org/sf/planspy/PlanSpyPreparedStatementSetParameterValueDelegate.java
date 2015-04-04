package org.sf.planspy;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;
import java.sql.Statement;

class PlanSpyPreparedStatementSetParameterValueDelegate implements Delegate {
    protected final PreparedStatementInformation preparedStatementInformation;

    public PlanSpyPreparedStatementSetParameterValueDelegate(PreparedStatementInformation preparedStatementInformation) {
        this.preparedStatementInformation = preparedStatementInformation;
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        // ignore calls to any methods defined on the Statement interface!
        if (!Statement.class.equals(method.getDeclaringClass())) {
            int position = (Integer) args[0];
            Object value = null;
            if (!method.getName().equals("setNull") && args.length > 1) {
                value = args[1];
            }
            preparedStatementInformation.setParameterValue(position, value);
        }
        return method.invoke(underlying, args);
    }
}
