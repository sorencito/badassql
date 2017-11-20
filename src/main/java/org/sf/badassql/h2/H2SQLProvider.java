package org.sf.badassql.h2;

import com.p6spy.engine.common.StatementInformation;
import org.sf.badassql.SQLExtractor;
import org.sf.badassql.SQLProvider;

/**
 * Provides plans for the H2 Database, version 1.4
 */
public class H2SQLProvider implements SQLProvider {

    @Override
    public String getSQLProvidingExecPlan(StatementInformation statementInformation) {
        String uppercaseSql = SQLExtractor.extractSQL(statementInformation);

        if (uppercaseSql.startsWith("SELECT")) {
            return "EXPLAIN " + uppercaseSql;
        } else {
            return null;
        }
    }
}