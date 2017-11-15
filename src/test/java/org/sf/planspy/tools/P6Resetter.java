package org.sf.planspy.tools;

import com.p6spy.engine.spy.P6ModuleManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class P6Resetter {
    public static void resetP6() {
        Method initMe = null;
        try {
            initMe =P6ModuleManager.class.getDeclaredMethod("initMe");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        initMe.setAccessible(true);
        try {
            initMe.invoke(P6ModuleManager.class, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
