package org.sf.planspy.h2;

import com.p6spy.engine.common.StatementInformation;
import org.sf.planspy.SQLProvider;

/**
 * Provides plans for the H2 Database, version 1.4
 */
public class H2SQLProvider implements SQLProvider {

    @Override
    public String getSQLProvidingExecPlan(StatementInformation statementInformation) {
        String uppercaseSql = getSQL(statementInformation).toUpperCase().trim();

        if (uppercaseSql.startsWith("SELECT")) {
            return "EXPLAIN " + uppercaseSql;
        } else {
            return null;
        }
    }

    private String getSQL(StatementInformation statementInformation) {
        String sql;

        if (statementInformation.getSqlWithValues().isEmpty()) {
            sql = statementInformation.getSql();
        } else {
            sql = statementInformation.getSqlWithValues();
        }

        if (sql != null)
            return sql;

        else return "";
    }
}