package org.sf.badassql;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6Factory;
import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

/*
* Initial bootstrapping process, hooking into @com.p6spy.engine.spy.P6SpyFactory
*/
public class BadasSQLFactory implements P6Factory {

    @Override
    public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
        return new BadasSQLOptions(optionsRepository);
    }

    @Override
    public JdbcEventListener getJdbcEventListener() {
        return BadasSQLJdbcEventListener.INSTANCE;
    }
}
