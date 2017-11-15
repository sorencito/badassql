package org.sf.planspy;

import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import javax.management.StandardMBean;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanSpyOptions extends StandardMBean implements PlanSpyOptionsMBean, P6LoadableOptions {

    public static final String SQLPROVIDER = "sqlprovider";
    public static String sqlProvider;
    private P6OptionsRepository optionsRepository;


    public PlanSpyOptions(P6OptionsRepository optionsRepository) {
        super(PlanSpyOptionsMBean.class, false);
        this.optionsRepository = optionsRepository;
    }

    @Override
    public void load(Map<String, String> options) {
        if (options != null)
            if (options.get(SQLPROVIDER) != null)
                sqlProvider = options.get(SQLPROVIDER);
    }

    @Override
    public Map<String, String> getDefaults() {
        Map<String, String> map = new HashMap<>();
        map.put(SQLPROVIDER, "org.sf.planspy.h2.H2SQLProvider");

        return map;
    }

}
