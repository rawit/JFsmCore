package org.jfsm.core.test.events.propertychange;

import java.beans.PropertyChangeEvent;

import org.jfsm.core.GuardAdapter;

/**
 * A guard condition for 'less than'. The constructor supplies the limit
 * value, and the event from the <code>evaluate</code> method is the value to
 * test against.
 * 
 * @author rwe
 * 
 */
public class IntLt extends GuardAdapter {

	private final int intValue;

	/**
	 * Constructor.
	 * 
	 * @param pIntValue the limit to test for 'less than'
	 */
	public IntLt(final int pIntValue) {
		this.intValue = pIntValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean evaluate(final Object event) {

		if (event instanceof PropertyChangeEvent) {
			final PropertyChangeEvent pce = (PropertyChangeEvent) event;
			final Integer newInt = (Integer) pce.getNewValue();
			if (newInt.intValue() < intValue) {
				return true;
			}
		}

		return false;
	}

}
