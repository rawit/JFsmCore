package org.jfsm.core.fluent;

import org.jfsm.StateI;
import org.jfsm.core.events.Event;

public class TransitionBuilder {

    private FsmBuilder fsmBuilder;

    public TransitionBuilder(FsmBuilder pFsmBuilder) {
        this.fsmBuilder = pFsmBuilder;
    }

    public StateI andGoTo(int stateId) {
        Event event = new Event(fsmBuilder.getEventType());
        StateI toState = fsmBuilder.getState(stateId);
        fsmBuilder.getFromState().addTransition(event, fsmBuilder.getGuard(), fsmBuilder.getAction(), toState); 
        return fsmBuilder.getFromState();
    }

}
