package org.jfsm.core.fluent;

import org.jfsm.GuardConditionI;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.events.Event;

public class GuardBuilder {

    private FsmBuilder fsmBuilder;

    public GuardBuilder(FsmBuilder pFsmBuilder) {
        this.fsmBuilder = pFsmBuilder;
    }

    public ActionBuilder andMatches(Object pGuard) {
        ActionBuilder actionBuilder = new ActionBuilder(fsmBuilder);
        this.fsmBuilder.setGuard(pGuard);
        return actionBuilder;
    }

    public JFsmModelI goTo(int stateId) {
        Event event = new Event(fsmBuilder.getEventType());
        StateI toState = fsmBuilder.getState(stateId);
        fsmBuilder.getFromState().addTransition(event, fsmBuilder.getGuard(), fsmBuilder.getAction(), toState); 
        return fsmBuilder.getJFsmModel();
    }

}
