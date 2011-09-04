package org.jfsm.core.guards;

import org.jfsm.GuardConditionI;
import org.jfsm.core.GuardAdapter;

/**
 * Guard for a logical 'and' between two guard conditions.
 */
public class AndGuard extends GuardAdapter {

	private final GuardConditionI gc1;

	private final GuardConditionI gc2;

	/**
	 * Constructor for the And guard object.
	 * 
	 * @param gc1 first guard condition
	 * @param gc2 second guard condition
	 */
	public AndGuard(final GuardConditionI gc1, final GuardConditionI gc2) {
		this.gc1 = gc1;
		this.gc2 = gc2;
	}

	/**
	 * Gets the logical expression of this guard as a string.
	 * 
	 * @return The expression value as a string
	 */
	@Override
	public String getExpression() {
		return gc1.getExpression() + " and " + gc2.getExpression();
	}

	/**
	 * Evaluate the expression.
	 * 
	 * @param event the event that was received
	 * @return true if the and'ed guards are true
	 */
	public boolean evaluate(final Object event) {
		return gc1.evaluate(event) && gc2.evaluate(event);
	}

}
