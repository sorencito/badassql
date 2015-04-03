package org.sf.planspy;

import com.p6spy.engine.spy.P6Factory;
import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class PlanSpyFactory implements P6Factory {

    @Override
    public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
        return null;
    }

    @Override
    public Connection getConnection(Connection conn) throws SQLException {
        return null;
    }
}
