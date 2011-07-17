package org.jfsm.core.guards;

import org.jfsm.GuardConditionI;
import org.jfsm.core.GuardAdapter;

/**
 * Adapter for a logical 'and' construct.
 */
public class AndGc extends GuardAdapter {

	private final GuardConditionI gc1;

	private final GuardConditionI gc2;

	/**
	 * Constructor for the ButtonSelected object.
	 * 
	 * @param gc1 first guard condition
	 * @param gc2 second guard condition
	 */
	public AndGc(final GuardConditionI gc1, final GuardConditionI gc2) {
		this.gc1 = gc1;
		this.gc2 = gc2;
	}

	/**
	 * Gets the Expression attribute of the ButtonSelected object.
	 * 
	 * @return The Expression value
	 */
	@Override
	public String getExpression() {
		return gc1.getExpression() + " and " + gc2.getExpression();
	}

	/**
	 * Evaluate the expression.
	 * 
	 * @param event the event that was received
	 * @return true if the button is deselected
	 */
	public boolean evaluate(final Object event) {
		return gc1.evaluate(event) && gc2.evaluate(event);
	}

}
