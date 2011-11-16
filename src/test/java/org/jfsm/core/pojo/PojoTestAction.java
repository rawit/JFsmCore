package org.jfsm.core.pojo;

import org.jfsm.core.annotations.ActionMethod;

public class PojoTestAction {

    @ActionMethod
    public void perform(String message) {
        System.out.println("Message: " + message);
    }

}
