package org.jfsm.core.fluent;

import org.jfsm.ActionI;
import org.jfsm.GuardConditionI;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.events.Event;
import org.jfsm.core.pojo.PojoGuardAdapter;

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
        final Object guard = fsmBuilder.getGuard();
        if (guard instanceof GuardConditionI) {
            fsmBuilder.getFromState().addTransition(event, (GuardConditionI) guard, fsmBuilder.getAction(), toState); 
        } else {
            PojoGuardAdapter pojoGuardAdapter = new PojoGuardAdapter(guard);
            fsmBuilder.getFromState().addTransition(event, pojoGuardAdapter, fsmBuilder.getAction(), toState);             
        }
        return fsmBuilder.getJFsmModel();
    }

}
