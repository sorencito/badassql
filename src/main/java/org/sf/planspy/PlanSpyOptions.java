package org.sf.planspy;

import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import javax.management.StandardMBean;
import java.util.Map;

public class PlanSpyOptions extends StandardMBean implements PlanSpyOptionsMBean, P6LoadableOptions {
    public PlanSpyOptions(P6OptionsRepository optionsRepository) {
        super(PlanSpyOptionsMBean.class, false);
    }

    @Override
    public void load(Map<String, String> options) {

    }

    @Override
    public Map<String, String> getDefaults() {
        return null;
    }
}
