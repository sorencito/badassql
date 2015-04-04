package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.GenericInvocationHandler;
import com.p6spy.engine.proxy.MethodNameMatcher;

import java.sql.Connection;

/**
 * Invocation handler for {@link java.sql.Connection}
 */
public class PlanSpyConnectionInvocationHandler extends GenericInvocationHandler<Connection> {

    public PlanSpyConnectionInvocationHandler(Connection underlying) {
        super(underlying);
        ConnectionInformation connectionInformation = new ConnectionInformation();

        PlanSpyConnectionCommitDelegate commitDelegate = new PlanSpyConnectionCommitDelegate(connectionInformation);
        PlanSpyConnectionRollbackDelegate rollbackDelegate = new PlanSpyConnectionRollbackDelegate(connectionInformation);
        PlanSpyConnectionPrepareStatementDelegate prepareStatementDelegate = new PlanSpyConnectionPrepareStatementDelegate(connectionInformation);
        PlanSpyConnectionCreateStatementDelegate createStatementDelegate = new PlanSpyConnectionCreateStatementDelegate(connectionInformation);
        PlanSpyConnectionPrepareCallDelegate prepareCallDelegate = new PlanSpyConnectionPrepareCallDelegate(connectionInformation);

        // add delegates to perform logging on connection methods
        addDelegate(
                new MethodNameMatcher("commit"),
                commitDelegate
        );
        addDelegate(
                new MethodNameMatcher("rollback"),
                rollbackDelegate
        );

        // add delegates to return proxies for other methods
        addDelegate(
                new MethodNameMatcher("prepareStatement"),
                prepareStatementDelegate
        );

        addDelegate(
                new MethodNameMatcher("createStatement"),
                createStatementDelegate
        );

        addDelegate(
                new MethodNameMatcher("prepareCall"),
                prepareCallDelegate
        );

        // TODO add proxy for getDatabaseMetaData - but not used for logging module?

    }

}
