package org.sf.planspy;

import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;

class PlanSpyResultSetGetColumnValueDelegate implements Delegate {


    private final ResultSetInformation resultSetInformation;

    public PlanSpyResultSetGetColumnValueDelegate(final ResultSetInformation resultSetInformation) {
        this.resultSetInformation = resultSetInformation;
    }

    /**
     * Called by the invocation handler instead of the target method.  Since this method is called
     * instead of the target method, it is up to implementations to invoke the target method (if applicable).
     *
     * @param proxy  The object being proxied
     * @param method The method that was invoked
     * @param args   The arguments of the method (if any).  This argument will be null if there
     *               were no arguments.
     * @return The return value of the method
     * @throws Throwable
     */
    @Override
    public Object invoke(final Object proxy, final Object underlying, final Method method, final Object[] args) throws Throwable {
        // the first argument will always be the column index or the column name
        String columnName = String.valueOf(args[0]);
        Object result = method.invoke(underlying, args);
        resultSetInformation.setColumnValue(columnName, result);
        return result;
    }
}
