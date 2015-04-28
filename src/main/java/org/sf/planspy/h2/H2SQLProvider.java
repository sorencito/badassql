package org.sf.planspy.h2;

import org.sf.planspy.QueryInformation;
import org.sf.planspy.SQLProvider;

public class H2SQLProvider implements SQLProvider {

    @Override
    public String getSQLProvidingExecPlan(QueryInformation queryInformation) {
        return "EXPLAIN " + queryInformation.getStatement();
    }
}