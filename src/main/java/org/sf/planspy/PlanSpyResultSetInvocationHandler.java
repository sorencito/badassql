package org.sf.planspy;

import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.proxy.GenericInvocationHandler;
import com.p6spy.engine.proxy.MethodNameAndParameterLikeMatcher;
import com.p6spy.engine.proxy.MethodNameMatcher;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Quinton McCombs
 * @since 09/2013
 */
class PlanSpyResultSetInvocationHandler extends GenericInvocationHandler<ResultSet> {

    /**
     * Creates a new invocation handler for the given object.
     *
     * @param underlying The object being proxied
     */
    public PlanSpyResultSetInvocationHandler(final ResultSet underlying, final StatementInformation statementInformation)
            throws SQLException {
        super(underlying);

        ResultSetInformation resultSetInformation = new ResultSetInformation(statementInformation);
        PlanSpyResultSetNextDelegate nextDelegate = new PlanSpyResultSetNextDelegate(resultSetInformation);
        PlanSpyResultSetCloseDelegate closeDelegate = new PlanSpyResultSetCloseDelegate(resultSetInformation);
        PlanSpyResultSetGetColumnValueDelegate getColumnValueDelegate = new PlanSpyResultSetGetColumnValueDelegate(resultSetInformation);

        addDelegate(
                new MethodNameMatcher("next"),
                nextDelegate
        );

        addDelegate(
                new MethodNameMatcher("close"),
                closeDelegate
        );

        // add delegates for the basic getXXXX(int) and getXXXX(String) methods
        addDelegate(
                new MethodNameAndParameterLikeMatcher("get*", int.class),
                getColumnValueDelegate
        );
        addDelegate(
                new MethodNameAndParameterLikeMatcher("get*", String.class),
                getColumnValueDelegate
        );
    }
}
