package org.jfsm.core.fluent;


public class EventBuilder {

    private FsmBuilder fsmBuilder;

    public EventBuilder(FsmBuilder pFsmBuilder) {
        this.fsmBuilder = pFsmBuilder;
    }

    public GuardBuilder receives(Class<?> pEventType) {        
        fsmBuilder.setEventType(pEventType);
        return new GuardBuilder(fsmBuilder);
    }

}
