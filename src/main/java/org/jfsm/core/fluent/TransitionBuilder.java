package org.jfsm.core.fluent;

import org.jfsm.GuardConditionI;
import org.jfsm.StateI;
import org.jfsm.core.events.Event;
import org.jfsm.core.pojo.PojoGuardAdapter;

public class TransitionBuilder {

    private FsmBuilder fsmBuilder;

    public TransitionBuilder(FsmBuilder pFsmBuilder) {
        this.fsmBuilder = pFsmBuilder;
    }

    public StateI andGoTo(int stateId) {
        Event event = new Event(fsmBuilder.getEventType());
        StateI toState = fsmBuilder.getState(stateId);
        final Object guard = fsmBuilder.getGuard();
        if (guard != null) {
            if (guard instanceof GuardConditionI) {
                fsmBuilder.getFromState()
                        .addTransition(event, (GuardConditionI) guard, fsmBuilder.getAction(), toState);
            } else {
                PojoGuardAdapter pojoGuardAdapter = new PojoGuardAdapter(guard);
                fsmBuilder.getFromState().addTransition(event, pojoGuardAdapter, fsmBuilder.getAction(), toState);
            }
        }
        return fsmBuilder.getFromState();
    }

}
