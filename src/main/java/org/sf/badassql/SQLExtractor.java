package org.sf.badassql;

import com.p6spy.engine.common.StatementInformation;

public class SQLExtractor {

    public static String extractSQL(StatementInformation statementInformation) {
        String sql = null;

        if (statementInformation.getSqlWithValues() != null) {
            if (statementInformation.getSqlWithValues().isEmpty()) {
                sql = statementInformation.getSql();
            } else {
                sql = statementInformation.getSqlWithValues();
            }
        }

        if (sql != null)
            return sql.toUpperCase().trim();

        else return "";
    }
}
