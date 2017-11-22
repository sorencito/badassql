package org.sf.badassql;

import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import javax.management.StandardMBean;
import java.util.HashMap;
import java.util.Map;

/**
 * The options found here can be used just as all other P6Spy Options either in the spy.properties or system properties.
 */
public class BadasSQLOptions extends StandardMBean implements BadasSQLOptionsMBean, P6LoadableOptions {

    public static final String SQLPROVIDER = "sqlprovider";
    public static final String REGEX1 = "regex1";

    private static Map<String, String> PROPERTIES = new HashMap<>();

    public BadasSQLOptions(P6OptionsRepository optionsRepository) {
        super(BadasSQLOptionsMBean.class, false);
    }

    @Override
    public void load(Map<String, String> options) {
        if (options == null)
            return;

        if (options.get(SQLPROVIDER) != null) {
            PROPERTIES.put(SQLPROVIDER, options.get(SQLPROVIDER));
        }

        if (options.get(REGEX1) != null)
            PROPERTIES.put(REGEX1, options.get(REGEX1));
    }

    @Override
    public Map<String, String> getDefaults() {
        Map<String, String> map = new HashMap<>();
        map.put(SQLPROVIDER, "org.sf.badassql.h2.H2SQLProvider");

        return map;
    }

    public static String getProperty(String property) {
        return PROPERTIES.get(property);
    }

    public static void resetAll() {
        PROPERTIES = new HashMap<>();
    }
}
