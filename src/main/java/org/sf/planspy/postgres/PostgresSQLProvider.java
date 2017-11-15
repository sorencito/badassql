package org.sf.planspy.postgres;

import com.p6spy.engine.common.StatementInformation;
import org.sf.planspy.SQLExtractor;
import org.sf.planspy.SQLProvider;

/**
 * Provides plans for the Postgres Database, version 9.1
 */
public class PostgresSQLProvider implements SQLProvider {

    @Override
    public String getSQLProvidingExecPlan(StatementInformation statementInformation) {
        String uppercaseSql = SQLExtractor.getSQL(statementInformation);

        if (uppercaseSql.startsWith("SELECT")) {
            return "EXPLAIN ANALYZE " + uppercaseSql;
        } else {
            return null;
        }
    }


}