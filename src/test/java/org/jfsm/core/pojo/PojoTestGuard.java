package org.jfsm.core.pojo;

import org.jfsm.core.annotations.GuardMethod;

public class PojoTestGuard {

    public static final String TRUE_MESSAGE = "This is the true message.";

    @GuardMethod
    public Boolean perform(String message) {

        if (message.equals(TRUE_MESSAGE)) {
            return true;
        }
        return false;
    }

}
