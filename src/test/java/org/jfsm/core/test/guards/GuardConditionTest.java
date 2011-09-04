package org.jfsm.core.test.guards;

import junit.framework.Assert;

import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.core.events.Event;
import org.jfsm.core.fsm.JFsm;
import org.jfsm.core.guards.IsCharGuard;
import org.junit.Test;

/**
 * @author rwe
 * 
 */
public class GuardConditionTest {

	@Test
	public void characterInputTest() throws Exception {

		// Create the two states
		final StateI state1 = new State(1);
		final StateI state2 = new State(2);

		// Create a guard condition that only accepts character 'A'
		final IsCharGuard isA = new IsCharGuard('A');
		// Create a guard condition that only accepts character 'A'
		final IsCharGuard isB = new IsCharGuard('B');

		// Create an event that only accepts character input
		final Event event = new Event(Character.class);

		// Add a transition from state 1 to state 2 when a character 'A' is
		// received
		state1.addTransition(event, isA, null, state2);

		// Add a transition from state 2 to state 1 when a character 'B' is
		// received
		state2.addTransition(event, isB, null, state1);

		// Create the model and add the states
		final JFsmModelI jfsmModel = new JFsmModel();
		jfsmModel.addState(state1);
		jfsmModel.addState(state2);
		// Tell the model that state 1 is the start state
		jfsmModel.setInitial(state1);

		// Create a FSM engine and start it
		final JFsm jFsm = new JFsm(jfsmModel);
		jFsm.start();

		// Make some valid and invalid input and test the FSM behavior for each
		// step
		jFsm.input('d');
		Assert.assertEquals("State was changed on invalid input", jFsm.getCurrentState(), state1);

		jFsm.input('A');
		Assert.assertEquals("State was not changed on valid input", jFsm.getCurrentState(), state2);

		jFsm.input('c');
		Assert.assertEquals("State was changed on invalid input", jFsm.getCurrentState(), state2);

		jFsm.input('A');
		Assert.assertEquals("State was changed on invalid input", jFsm.getCurrentState(), state2);

		jFsm.input('B');
		Assert.assertEquals("State was not changed on valid input", jFsm.getCurrentState(), state1);

	}

}
