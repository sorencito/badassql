package org.sf.planspy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryAnalyser {
    private QueryInformation queryInformation;
    private String plan;

    public QueryAnalyser(QueryInformation queryInformation) {
        this.queryInformation = queryInformation;
    }

    public void run() {
        try {
            ResultSet s = queryInformation.getConnection().createStatement().executeQuery("EXPLAIN " + queryInformation.getStatement());

            if (s != null) {
                s.next();
                plan = s.getString(1);
            }
        } catch (SQLException e) {
            // swallow
        }
    }

    public boolean hasPlanBeenProduced() {
        return plan != null;
    }

    public String getPlan() {
        return plan;
    }
}
