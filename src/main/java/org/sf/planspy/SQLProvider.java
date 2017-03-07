package org.sf.planspy;

import com.p6spy.engine.common.StatementInformation;

/**
 * Extension point for different databases.
 */
public interface SQLProvider {
    /**
     * Returns the final SQL which will subsequently be used for querying the database for the execution plan for the
     * specific database.
     *
     * @param statementInformation the statementInformation
     * @return the SQL providing the execution plan for the specific database
     */
    String getSQLProvidingExecPlan(StatementInformation statementInformation);
}
