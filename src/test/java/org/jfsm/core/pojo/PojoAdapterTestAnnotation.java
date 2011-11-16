package org.jfsm.core.pojo;

import java.util.LinkedList;
import java.util.List;

import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.core.events.Event;
import org.junit.Before;

public class PojoAdapterTestAnnotation extends PojoAdapterTestPropeties {

    @Before
    public void setUp() {

        actionAdapter = new PojoActionAdapter(new PojoTestAction());
        guardAdapter = new PojoGuardAdapter(new PojoTestGuard());

        state1 = new State(1);
        state2 = new State(2);
        Event event = new Event(String.class);
        state1.addTransition(event, guardAdapter, actionAdapter, state2);

        jFsmModel = new JFsmModel();
        List<StateI> states = new LinkedList<StateI>();
        states.add(state1);
        states.add(state2);
        jFsmModel.setStates(states);
        jFsmModel.setStartState(state1);
    }

}
