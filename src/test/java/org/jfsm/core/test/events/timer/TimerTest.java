package org.jfsm.core.test.events.timer;

import java.util.Date;

import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.core.events.After;
import org.jfsm.core.events.When;
import org.jfsm.core.fsm.FsmEventListener;
import org.jfsm.core.fsm.JFsm;
import org.jfsm.core.fsm.events.FsmEvent;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the timer events.
 */
public class TimerTest {

	private static final int WAIT_TIME = 2000;

	private static final int AFTER_TIME = 100;

	private static final int WHEN_TIME = 1000;

	/**
	 * Test timer events.
	 * Create a state machine that has the following behavior: 
     * Move between State1 and State2 according to AFTER_TIME (relative time according to state entry).
 	 * When State2 reaches the WHEN_TIME (absolute time), move to State 3 and terminate.
	 */
	@Test
	public void testTimers() {

		final JFsmModel fsmModel = new JFsmModel();

		final State state1 = new State(1, "State 1");
		final State state2 = new State(2, "State 2");
		final State state3 = new State(3, "State 3");

		state1.addTransition(new After(AFTER_TIME, true), null, null, state2);
		state2.addTransition(new After(AFTER_TIME, true), null, null, state1);

		final When when = new When(new Date(System.currentTimeMillis() + WHEN_TIME));
		state2.addTransition(when, null, null, state3);
		state3.setFinal(true);

		fsmModel.addState(state1);
		fsmModel.addState(state2);
		fsmModel.addState(state3);
		fsmModel.setInitial(state1);

		final JFsm fsm = new JFsm(fsmModel);
		fsm.start();

		try {
			Thread.sleep(WAIT_TIME);
		} catch (final InterruptedException ite) {
			Assert.fail();
		}

		Assert.assertEquals(state3, fsm.getCurrentState());

	}

}
