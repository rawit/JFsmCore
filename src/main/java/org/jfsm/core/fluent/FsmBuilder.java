package org.jfsm.core.fluent;

import java.util.HashMap;
import java.util.Map;

import org.jfsm.ActionI;
import org.jfsm.GuardConditionI;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;

public class FsmBuilder {
    
    private JFsmModelI model = null;

    private final Map<Integer, StateI> states = new HashMap<Integer, StateI>();

    private StateI fromState;

    private Object guard;

    private Class<?> eventType;

    private ActionI action;

    public FsmBuilder(final int pStateI1) {
        model = new JFsmModel();
        model.setInitial(getState(pStateI1));
    }

    public FsmBuilder(final JFsmModelI jFsmModel) {
        model = jFsmModel;
    }

    public JFsmModelI getJFsmModel() {
        return this.model;
    }

    public StateI getState(int pStateId) {
        StateI state = states.get(pStateId);
        if (state == null) {
            final State newState = new State(pStateId);
            model.addState(newState);
            return newState;
        }
        return state;
    }

    public void setFromState(StateI state) {
        this.fromState = state;
    }

    public StateI getFromState() {
        return fromState;
    }

    public void setEventType(Class<?> pEventType) {
        this.eventType = pEventType;
    }

    public Class<?> getEventType() {
        return this.eventType;
    }

    public void setGuard(Object pGuard) {
        this.guard = pGuard;
    }

    public Object getGuard() {
        return this.guard;
    }

    public ActionI getAction() {
        return action;
    }

    public void setAction(ActionI pAction) {
        this.action = pAction;        
    }

}
