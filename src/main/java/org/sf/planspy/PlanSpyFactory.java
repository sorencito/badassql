package org.sf.planspy;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.proxy.ProxyFactory;
import com.p6spy.engine.spy.P6Factory;
import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class PlanSpyFactory implements P6Factory {

    public static final String activationMsg = "PlanSpyFactory active";

    @Override
    public Connection getConnection(Connection conn) throws SQLException {
        PlanSpyConnectionInvocationHandler invocationHandler = new PlanSpyConnectionInvocationHandler(conn);
        P6LogQuery.getLogger().logText(activationMsg);
        return ProxyFactory.createProxy(conn, invocationHandler);
    }

    @Override
    public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
        return new PlanSpyOptions(optionsRepository);
    }
}
