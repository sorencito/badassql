package org.sf.planspy;

/**
 * Created by S�ren on 28.04.2015.
 */
public interface SQLProvider {
    String getSQLProvidingExecPlan(QueryInformation queryInformation);
}
