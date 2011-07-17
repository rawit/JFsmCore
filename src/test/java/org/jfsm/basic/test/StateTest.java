package org.jfsm.basic.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jfsm.basic.State;
import org.jfsm.fsm.FsmState;
import org.junit.Test;

/**
 * Test code for the StateTest class.
 */
public class StateTest extends TestCase {

    /**
     * Constructor for the Test object.
     */
    @Test
    public void testConstructorArguments() {
        new State(1, "name");
    }

    /**
     * Constructor for the Test object.
     */
    @Test
    public void testNullArgumentsOnMethods() {
        final State state1 = new State(1, (String) null);
        state1.addEntryAction(null, true);
        state1.addExitAction(null);
        state1.addInternalTransition(null, null, null);
        try {
            state1.addTransition(null, null, null, null);
            fail("State.addTransition(); should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }

        state1.setName(null);
    }

    /**
     * Constructor for the Test object.
     */
    @Test
    public void testStartStop() {

        final State state1 = new State(1, (String) null);
        final FsmState fsmState = new FsmState(state1, null);
        fsmState.entering(null);
        fsmState.exiting();

    }

}
