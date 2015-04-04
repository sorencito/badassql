package org.sf.planspy;

import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.proxy.Delegate;

import java.lang.reflect.Method;

class PlanSpyResultSetNextDelegate implements Delegate {
    private final ResultSetInformation resultSetInformation;

    public PlanSpyResultSetNextDelegate(final ResultSetInformation resultSetInformation) {
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
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {

            if (resultSetInformation.getCurrRow() > -1) {
        /*
           Log the columns that were accessed except on the first call to ResultSet.next().  The first time it is
           called it is advancing to the first row.  
          */
                resultSetInformation.generateLogMessage();
            }

            result = method.invoke(underlying, args);

            if (Boolean.TRUE.equals(result)) {
                // only increment the current row number if the result set advanced to the next row
                resultSetInformation.setCurrRow(resultSetInformation.getCurrRow() + 1);
            }

            return result;
        } finally {
            // the result of the proxied method call will be true or false since this is used to proxy the call to ResultSet.next()
            // we do not need to log the call if the result was false as it means that there were no more results.
            if (Boolean.TRUE.equals(result)) {
//                P6LogQuery.logElapsed(resultSetInformation.getConnectionId(), startTime, Category.RESULT, resultSetInformation);
            }
        }
    }
}
