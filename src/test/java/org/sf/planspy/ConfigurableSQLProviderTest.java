package org.sf.planspy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import org.junit.Test;
import org.sf.planspy.tools.P6Resetter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigurableSQLProviderTest {

    @Test
    public void configurableViaSystemProperty() {
        System.setProperty("p6spy.config.sqlprovider", "org.sf.planspy.postgres.PostgresSQLProvider");
        P6Resetter.resetP6();
        QueryAnalyser analyser = new QueryAnalyser(new StatementInformation(ConnectionInformation.fromDriver(null, null, 0)));
        assertThat(analyser.getSQLProviderClass(), is(equalTo("org.sf.planspy.postgres.PostgresSQLProvider")));
    }

    @Test
    public void configurableViaDefaultProperty() {
        System.clearProperty("p6spy.config.sqlprovider");
        P6Resetter.resetP6();
        QueryAnalyser analyser = new QueryAnalyser(new StatementInformation(ConnectionInformation.fromDriver(null, null, 0)));
        assertThat(analyser.getSQLProviderClass(), is(equalTo("org.sf.planspy.h2.H2SQLProvider")));
    }

}