package org.sf.badassql.tools;

import com.p6spy.engine.spy.P6ModuleManager;
import org.sf.badassql.BadasSQLOptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Resetter {

    public static void resetProperties() {
        System.clearProperty("p6spy.config.regex1");
        System.clearProperty("p6spy.config.sqlprovider");
        BadasSQLOptions.resetAll();
    }

    public static void resetP6() {
        Method initMe = null;
        try {
            initMe =P6ModuleManager.class.getDeclaredMethod("initMe");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (initMe != null) {
            initMe.setAccessible(true);
        }else {
            throw new IllegalStateException("seems like the #initMe method disappeared in a new version of P6Spy (at the time of coding 3.5.1)");
        }
        try {
            initMe.invoke(P6ModuleManager.class);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
