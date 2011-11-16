package org.jfsm.core.pojo;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.core.Transition;
import org.jfsm.core.events.Event;
import org.jfsm.core.fsm.JFsm;
import org.junit.Before;
import org.junit.Test;

public class PojoAdapterTestPropeties {

    protected PojoActionAdapter actionAdapter;

    protected PojoGuardAdapter guardAdapter;

    protected Transition transition;

    protected StateI state1;

    protected StateI state2;

    protected JFsmModel jFsmModel;

    @Before
    public void setUp() {

        actionAdapter = new PojoActionAdapter(new PojoTestAction(), "perform");
        guardAdapter = new PojoGuardAdapter(new PojoTestGuard(), "perform");

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

    @Test
    public void testActionAdapter() {

        actionAdapter.execute("this is the message");

    }

    @Test
    public void testGuardAdapter() {

        transition = new Transition(state1, new Event(String.class), this.guardAdapter, this.actionAdapter, state2);

        Assert.assertTrue(guardAdapter.evaluate(PojoTestGuard.TRUE_MESSAGE));
        Assert.assertFalse(guardAdapter.evaluate("Blabla"));

    }

    @Test
    public void testTransition() {

        transition = new Transition(state1, new Event(String.class), this.guardAdapter, this.actionAdapter, state2);

        Assert.assertTrue(transition.evaluate(PojoTestGuard.TRUE_MESSAGE));
        Assert.assertFalse(transition.evaluate("BlahBlah..."));

    }

    @Test
    public void testFsm() throws Exception {
                        
        JFsm jFsm = new JFsm(jFsmModel);
        jFsm.start();
        jFsm.input(PojoTestGuard.TRUE_MESSAGE);
            
        Assert.assertTrue("Wrong state, was: " + jFsm.getCurrentState(), jFsm.getCurrentState() == state2);

    }

}
