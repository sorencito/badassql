package org.sf.badassql.postgres;

import com.p6spy.engine.common.StatementInformation;
import org.sf.badassql.SQLExtractor;
import org.sf.badassql.SQLProvider;

/**
 * Provides plans for the Postgres Database, version 9.1
 */
public class PostgresSQLProvider implements SQLProvider {

    @Override
    public String getSQLProvidingExecPlan(StatementInformation statementInformation) {
        String uppercaseSql = SQLExtractor.extractSQL(statementInformation);

        if (uppercaseSql.startsWith("SELECT")) {
            return "EXPLAIN ANALYZE " + uppercaseSql;
        } else {
            return null;
        }
    }


}