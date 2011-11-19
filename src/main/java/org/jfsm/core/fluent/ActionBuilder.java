package org.jfsm.core.fluent;

import org.jfsm.ActionI;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.events.Event;

public class ActionBuilder {

    private FsmBuilder fsmBuilder;

    public ActionBuilder(FsmBuilder pFsmBuilder) {
        this.fsmBuilder = pFsmBuilder;
    }

    public TransitionBuilder thenExecute(ActionI pAction) {
        fsmBuilder.setAction(pAction);
        TransitionBuilder transitionBuilder = new TransitionBuilder(fsmBuilder);
        return transitionBuilder;
    }

    public JFsmModelI goTo(int stateId) {
        Event event = new Event(fsmBuilder.getEventType());
        StateI toState = fsmBuilder.getState(stateId);
        fsmBuilder.getFromState().addTransition(event, fsmBuilder.getGuard(), fsmBuilder.getAction(), toState); 
        return fsmBuilder.getJFsmModel();
    }

}
