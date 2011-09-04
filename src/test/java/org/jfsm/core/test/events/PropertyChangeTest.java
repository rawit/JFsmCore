package org.jfsm.core.test.events;

import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.core.events.FsmPropertyChangeEvent;
import org.jfsm.core.fsm.JFsm;
import org.junit.Assert;
import org.junit.Test;

/**
 * Use property change events on a JavaBean to drive the state changes. Set up
 * at <code>TestBean</code> class who emits property change events using
 * standard bean property change mechanisms. Then use a JFsm property
 * change event, <code>FsmPropertyChangeEvent</code> to catch these and input
 * to the FSM. The <code>IntGt</code> guard condition test the changed value and
 * says 'true' when the value greater than 5 has been set using the
 * <code>setX()</code> method and the <code>IntLt</code> does the other way around.
 * 
 * @author rwe
 */
public class PropertyChangeTest {

	/**
	 * Test that JFsmPropertyChange events leads to the expected state changes.
	 */
	@Test
	public void testPropertyChange() {

		// Create states and add to the model
		final StateI state1 = new State(1);
		final StateI state2 = new State(2);
		final JFsmModelI fsmModel = new JFsmModel();
		fsmModel.addState(state1);
		fsmModel.addState(state2);
		fsmModel.setInitial(state1);

		// Create a property change listener and register it with the test bean.
		// Then use the listener as an FSM event
		final FsmPropertyChangeEvent pce = new FsmPropertyChangeEvent();
		final TestBean testBean = new TestBean();
		testBean.addPropertyChangeListener(pce);
		// Add transitions to the states that makes the state change to state 2 
		// whenever the set x value are greater than 5 ...
		state1.addTransition(pce, new IntGt(5), null, state2);
		// ... or back to state 1 when less than 5
		state2.addTransition(pce, new IntLt(5), null, state1);

		// Create a state machine and start it
		final JFsm jFsm = new JFsm(fsmModel);
		jFsm.start();
		
		Assert.assertEquals(state1, jFsm.getCurrentState());

		// Set values on the test bean and assert proper changes in state
		testBean.setX(4);
		Assert.assertEquals(state1, jFsm.getCurrentState());
		testBean.setX(5);
		Assert.assertEquals(state1, jFsm.getCurrentState());
		testBean.setX(6);
		Assert.assertEquals(state2, jFsm.getCurrentState());
		testBean.setX(4);
		Assert.assertEquals(state1, jFsm.getCurrentState());
		testBean.setX(5);
		Assert.assertEquals(state1, jFsm.getCurrentState());
		testBean.setX(8);
		Assert.assertEquals(state2, jFsm.getCurrentState());

	}

}
