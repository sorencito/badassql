package org.sf.planspy;

import java.sql.Connection;

public class QueryInformation {
    private Connection connection;
    private String statement;

    public QueryInformation(Connection connection, String arg) {
        this.connection = connection;
        this.statement = arg;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getStatement() {
        return statement;
    }
}
