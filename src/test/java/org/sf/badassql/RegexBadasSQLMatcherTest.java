package org.sf.badassql;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import org.junit.After;
import org.junit.Test;
import org.sf.badassql.tools.Resetter;

public class RegexBadasSQLMatcherTest {

    @After
    public void tearDown() {
        Resetter.resetProperties();
    }

    @Test(expected = BadasSQLException.class)
    public void matchTooManyJoinsTest() {
        System.setProperty("p6spy.config.regex1", ".*(.*join.*){5,}.*");
        Resetter.resetP6();
        StatementInformation statementInformation = new StatementInformation(ConnectionInformation.fromDriver(null, null, 0));
        statementInformation.setStatementQuery("select name, colour from " +
                "cars left join " +
                "other_stuff outer join " +
                "other_stuff natural join on " +
                "other_stuff join " +
                "other_stuff join " +
                "where name = %123%");

        new QueryAnalyser(statementInformation).run();
    }

}