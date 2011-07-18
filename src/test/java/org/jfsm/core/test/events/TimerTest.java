package org.jfsm.core.test.events;

import java.util.Date;

import org.jfsm.core.ActionAdapter;
import org.jfsm.core.GuardAdapter;
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
public class TimerTest implements FsmEventListener {

	private static final int WAIT_TIME = 2000;

	private static final int AFTER_TIME = 100;

	private static final int WHEN_TIME = 1000;

	private static int counter = 0;

	/**
	 * Test timer events.
	 */
	@Test
	public void testTimers() {

		final JFsmModel fsmModel = new JFsmModel();

		final State state1 = new State(1, "State 1");
		final State state2 = new State(2, "State 2");
		final State state3 = new State(3, "State 3");

		final When event = new When(new Date(System.currentTimeMillis() + WHEN_TIME));
		state2.addTransition(event, null, null, state3);
		state3.setFinal(true);

		fsmModel.addState(state1);
		fsmModel.addState(state2);
		fsmModel.addState(state3);
		fsmModel.setInitial(state1);

		state1.addEntryAction(new ResetCounter(), true);
		state2.addEntryAction(new ResetCounter(), true);
		state1.addTransition(new After(AFTER_TIME, true), new CounterReachedLimit(), null, state2);
		state2.addTransition(new After(AFTER_TIME, true), new CounterReachedLimit(), null, state1);

		final JFsm fsm = new JFsm(fsmModel);
		fsm.addChangeListener(this);
		fsm.start();
		fsm.input(new Object());

		try {
			Thread.sleep(WAIT_TIME);
		} catch (final InterruptedException ite) {
			Assert.assertTrue(true);
		}

		Assert.assertEquals(state3.getIdentifier(), fsm.getCurrentState().getIdentifier());

	}

	/**
	 * Description of the Method.
	 * 
	 * @param event
	 *            Description of Parameter
	 */
	public void onEvent(final FsmEvent event) {
		// System.out.println("TimerTest: onEvent: Event = " +
		// event.getMessage());
	}

	/**
	 * Description of the Class.
	 */
	class CounterReachedLimit extends GuardAdapter {

		private static final int COUNT_LIMIT = 5;

		/**
		 * Gets the Expression attribute of the CounterReachedLimit object.
		 * 
		 * @return The Expression value
		 */
		@Override
		public String getExpression() {
			return "(counter++ > 5) == " + (counter > COUNT_LIMIT) + " ( " + counter + ")";
		}

		/**
		 * Description of the Method.
		 * 
		 * @param triggeringEvent
		 *            Description of Parameter
		 * @return Description of the Returned Value
		 */
		public boolean evaluate(final Object triggeringEvent) {
			if (counter++ > 2) {
				return true;
			}
			return false;
		}

		/**
		 * Description of the Method.
		 * 
		 * @return Description of the Returned Value
		 */
		@Override
		public String toString() {
			return "CounterReachedLimit";
		}
	}

	/**
	 * Description of the Class.
	 */
	class ResetCounter extends ActionAdapter {

		/**
		 * Gets the Expression attribute of the ResetCounter object.
		 * 
		 * @return The Expression value
		 */
		@Override
		public String getExpression() {
			return "counter = 0 ( value == " + counter + ")";
		}

		/**
		 * Description of the Method.
		 * 
		 * @param triggeringEvent
		 *            Description of Parameter
		 */
		public void execute(final Object triggeringEvent) {
			counter = 0;
		}

		/**
		 * Description of the Method.
		 * 
		 * @return Description of the Returned Value
		 */
		@Override
		public String toString() {
			return "ResetCounter";
		}

		@Override
		public void setHasBeenExecuted(final boolean pHasBeenExecuted) {
			// TODO Auto-generated method stub
		}

	}

}
