package org.sf.planspy;

import com.p6spy.engine.common.CallableStatementInformation;

import java.lang.reflect.Method;
import java.sql.Statement;

class PlanSpyCallableStatementSetParameterValueDelegate extends PlanSpyPreparedStatementSetParameterValueDelegate {

    public PlanSpyCallableStatementSetParameterValueDelegate(CallableStatementInformation callableStatementInformation) {
        super(callableStatementInformation);
    }

    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        if (args[0] instanceof Integer) {
            return super.invoke(proxy, underlying, method, args);
        } else {
            // ignore calls to any methods defined on the Statement interface!
            if (!Statement.class.equals(method.getDeclaringClass())) {
                String name = (String) args[0];
                Object value = null;
                if (!method.getName().equals("setNull") && args.length > 1) {
                    value = args[1];
                }
                ((CallableStatementInformation) preparedStatementInformation).setParameterValue(name, value);
            }
        }
        return method.invoke(underlying, args);
    }


}
