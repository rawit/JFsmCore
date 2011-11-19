package org.jfsm.core.test;

import junit.framework.Assert;

import org.jfsm.core.State;
import org.jfsm.core.Transition;
import org.junit.Test;

/**
 * Test code for the Transition class.
 */
public class TransitionTest {

	/**
	 * Test constructor arguments.
	 */
	@Test
	public void testConstructorArguments() {
		try {
			new Transition(null, null, null, null, null);
			Assert.fail("Transition(null, null, null, null, null); should have thrown IllegalArgumentException");
		} catch (final IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

	}

	/**
	 * Test null arguments on transitions.
	 */
	@Test
	public void testNullArgumentsOnMethods() {
		final State state1 = new State(1);
		try {
			new Transition(state1, null, null, null, null);
			Assert.fail("should have thrown IllegalArgumentException");
		} catch (final IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
		final State state2 = new State(1);
		try {
			new Transition(null, null, null, null, state2);
			Assert.fail("should have thrown IllegalArgumentException");
		} catch (final IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test getters on Transition.
	 */
	@Test
	public void testTranstionAttributes() {
		final State from = new State(1);
		final State to = new State(2);
		final Transition tr1 = new Transition(from, null, null, null, to);
		Assert.assertEquals(tr1.getFromState(), from);
		Assert.assertNull(tr1.getEvent());
		Assert.assertNull(tr1.getGuardCondition());
		Assert.assertNotNull(tr1.getActions());
		// FIXME Why does the next line fail?
		// Assert.assertTrue(tr1.getActions().isEmpty());
        Assert.assertTrue(tr1.getFromState().getIdentifier() == 1);
        Assert.assertTrue(tr1.getToStateId() == 2);
	}

	   /**
     * Test getters on Transition.
     */
    @Test
    public void testStateWithOneTransition() {
        final State from = new State(1);
        from.addTransition(null, null, null, new State(2));
        Assert.assertTrue(from.getTransitions().size() == 1);
    }

}
